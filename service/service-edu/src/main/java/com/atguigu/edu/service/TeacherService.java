package com.atguigu.edu.service;

import com.atguigu.edu.entity.TeacherEntity;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-05-21
 */
public interface TeacherService extends IService<TeacherEntity> {
    void pageQuery(Page<TeacherEntity> pageParam, TeacherQuery teacherQuery);
}
