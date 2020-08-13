package com.atguigu.guli.service.acl.service;

import com.atguigu.guli.service.acl.entity.PermissionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-12
 */
public interface PermissionService extends IService<PermissionEntity> {

    List<PermissionEntity> queryAllMenu();

    void removeChildById(String id);

    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);
}
