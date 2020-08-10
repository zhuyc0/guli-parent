package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.service.edu.entity.CourseCollectEntity;
import com.atguigu.guli.service.edu.entity.vo.CourseCollectVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
public interface CourseCollectService extends IService<CourseCollectEntity> {

    boolean isCollect(String courseId, String memberId);

    void saveCourseCollect(String courseId, String memberId);

    List<CourseCollectVo> selectListByMemberId(String memberId);

    void removeCourseCollect(String courseId, String memberId);
}
