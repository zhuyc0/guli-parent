package com.atguigu.oss.service.impl;

import com.atguigu.oss.entity.TeacherEntity;
import com.atguigu.oss.entity.vo.TeacherQuery;
import com.atguigu.oss.mapper.TeacherMapper;
import com.atguigu.oss.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-05-21
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherEntity> implements TeacherService {

    @Override
    public void pageQuery(Page<TeacherEntity> pageParam, TeacherQuery teacherQuery) {
        LambdaQueryWrapper<TeacherEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(TeacherEntity::getSort);

        if (teacherQuery == null) {
            this.page(pageParam, queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        LocalDateTime begin = teacherQuery.getBegin();
        LocalDateTime end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(TeacherEntity::getName, name);
        }

        if (level != null) {
            queryWrapper.eq(TeacherEntity::getLevel, level);
        }

        if (begin != null) {
            queryWrapper.ge(TeacherEntity::getGmtCreate, begin);
        }

        if (end != null) {
            queryWrapper.le(TeacherEntity::getGmtCreate, end);
        }
        queryWrapper.orderByDesc(TeacherEntity::getGmtCreate);
        this.page(pageParam, queryWrapper);
    }
}
