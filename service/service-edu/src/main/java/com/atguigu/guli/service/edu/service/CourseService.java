package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.CourseEntity;
import com.atguigu.guli.service.edu.entity.form.CourseInfoForm;
import com.atguigu.guli.service.edu.entity.form.CourseQueryForm;
import com.atguigu.guli.service.edu.entity.form.WebCourseQueryForm;
import com.atguigu.guli.service.edu.entity.vo.WebCourseVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
public interface CourseService extends IService<CourseEntity> {

    R webSelectList(WebCourseQueryForm webCourseQuery);

    WebCourseVo getWebCourseById(String courseId);

    List<CourseEntity> getHotCourse();

    R getCourseInfoById(String id);

    R saveCourseInfo(CourseInfoForm courseInfoForm);

    void updateCourseInfoByIf(CourseInfoForm courseInfoForm);

    R queryPage(Long page, Long limit, CourseQueryForm courseQuery);

    void removeCoverById(String id);

    void removeCourseById(String id);

    R getCoursePublishVoById(String id);

    void publishCourseById(String id);
}
