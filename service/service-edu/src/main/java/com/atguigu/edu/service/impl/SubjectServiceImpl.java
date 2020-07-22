package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.SubjectEntity;
import com.atguigu.edu.entity.excel.SubjectData;
import com.atguigu.edu.entity.vo.SubjectVO;
import com.atguigu.edu.listener.SubjectExcelListener;
import com.atguigu.edu.mapper.SubjectMapper;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-22
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectEntity> implements SubjectService {

    @Override
    public R saveSubject(MultipartFile file) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(this)).sheet().doRead();
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @Override
    public R tree() {
        // 数据量小,查询所有
        List<SubjectEntity> list = this.list();
        // 为空 提前结束
        if (CollectionUtils.isEmpty(list)) {
            return R.ok().data("list", Collections.emptyList());
        }
        // DO 转 VO ,mybatis-xml-mapper映射麻烦
        List<SubjectVO> subjects = list.stream()
                .map(x -> new SubjectVO().setId(x.getId()).setPId(x.getParentId()).setTitle(x.getTitle()))
                .collect(Collectors.toList());
        // 分组
        Map<String, List<SubjectVO>> collect = subjects.stream().collect(Collectors.groupingBy(SubjectVO::getPId));
        // 获取顶级分类
        List<SubjectVO> root = collect.get("0");
        // 递归生成树
        tree(root, collect);
        return R.ok().data("list", root);
    }

    private void tree(List<SubjectVO> root, Map<String, List<SubjectVO>> collect) {
        root.forEach(x -> {
            List<SubjectVO> children = collect.get(x.getId());
            if (CollectionUtils.isEmpty(children)) {
                x.setChildren(Collections.emptyList());
            } else {
                tree(children, collect);
                x.setChildren(children);
            }
        });
    }
}
