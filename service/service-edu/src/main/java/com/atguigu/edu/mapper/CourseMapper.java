package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.CourseEntity;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-23
 */
public interface CourseMapper extends BaseMapper<CourseEntity> {

    CoursePublishVo publishCourseInfo(String id);
}
