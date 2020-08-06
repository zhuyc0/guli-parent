package com.atguigu.guli.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.easyexcel.ExcelSubjectData;
import com.atguigu.guli.service.edu.easyexcel.ExcelSubjectDataListener;
import com.atguigu.guli.service.edu.entity.SubjectEntity;
import com.atguigu.guli.service.edu.entity.vo.SubjectVo;
import com.atguigu.guli.service.edu.mapper.SubjectMapper;
import com.atguigu.guli.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectEntity> implements SubjectService {

    @Override
    public R nestedList() {

        List<SubjectEntity> list = baseMapper.selectList(null);

        List<SubjectVo> collect = list.stream().map(x -> {
            SubjectVo vo = new SubjectVo();
            BeanUtils.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());

        Map<String, List<SubjectVo>> mapList = collect.stream().collect(Collectors.groupingBy(SubjectVo::getParentId));

        List<SubjectVo> root = mapList.get("0");
        tree(root, mapList);
        return R.ok().data("items", root);
    }

    @Override
    public void batchImport(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectDataListener(baseMapper))
                .sheet().doRead();
    }

    private void tree(List<SubjectVo> root, Map<String, List<SubjectVo>> mapList) {
        root.forEach(x -> {
            List<SubjectVo> vos = mapList.get(x.getId());
            if (!CollectionUtils.isEmpty(vos)) {
                tree(vos, mapList);
                x.setChildren(vos);
            }
        });
    }
}
