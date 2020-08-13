package com.atguigu.guli.service.acl.service.impl;

import com.atguigu.guli.service.acl.entity.PermissionEntity;
import com.atguigu.guli.service.acl.entity.RolePermissionEntity;
import com.atguigu.guli.service.acl.mapper.PermissionMapper;
import com.atguigu.guli.service.acl.service.PermissionService;
import com.atguigu.guli.service.acl.service.RolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public List<PermissionEntity> queryAllMenu() {
        LambdaQueryWrapper<PermissionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PermissionEntity::getId);
        List<PermissionEntity> permissions = baseMapper.selectList(wrapper);

        Map<String, List<PermissionEntity>> collect = permissions.stream().collect(Collectors.groupingBy(PermissionEntity::getPid));
        List<PermissionEntity> root = collect.get("0");
        buildTree(root, collect);
        return root;
    }

    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);
        //把根据节点id放到list中
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionId) {
        rolePermissionService.remove(new LambdaQueryWrapper<RolePermissionEntity>().eq(RolePermissionEntity::getRoleId, roleId));
        List<RolePermissionEntity> collect = Arrays.stream(permissionId).map(x -> new RolePermissionEntity().setRoleId(roleId)
                .setPermissionId(x)).collect(Collectors.toList());
        rolePermissionService.saveBatch(collect);
    }

    /**
     * 递归获取子节点
     */
    private void selectChildListById(String id, List<String> idList) {
        List<PermissionEntity> childList = baseMapper.selectList(new LambdaQueryWrapper<PermissionEntity>()
                .eq(PermissionEntity::getPid, id).select(PermissionEntity::getId));
        childList.parallelStream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    private void buildTree(List<PermissionEntity> root, Map<String, List<PermissionEntity>> collect) {
        root.forEach(x -> {
            List<PermissionEntity> children = collect.get(x.getId());
            if (CollectionUtils.isEmpty(children)) {
                x.setChildren(Collections.emptyList());
            } else {
                buildTree(children, collect);
                x.setChildren(children);
            }
        });
    }
}
