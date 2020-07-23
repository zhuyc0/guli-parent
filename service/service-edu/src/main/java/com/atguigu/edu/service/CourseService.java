package com.atguigu.edu.service;

import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.CourseEntity;
import com.atguigu.edu.entity.vo.CourseInfoForm;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-23
 */
public interface CourseService extends IService<CourseEntity> {

    R saveCourseInfo(CourseInfoForm courseInfo);

    R pageList(CourseQuery cq);

    R getCourseInfo(String courseId);

    R updateCourseInfo(CourseInfoForm courseInfo);
}
