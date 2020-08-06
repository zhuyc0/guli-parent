package com.atguigu.guli.service.edu.mapper;

import com.atguigu.guli.service.edu.entity.CourseEntity;
import com.atguigu.guli.service.edu.entity.vo.CoursePublishVo;
import com.atguigu.guli.service.edu.entity.vo.CourseVo;
import com.atguigu.guli.service.edu.entity.vo.WebCourseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
public interface CourseMapper extends BaseMapper<CourseEntity> {
    WebCourseVo selectWebCourseVoById(String courseId);

    List<CourseVo> selectPageByCourseQueryVo(Page<CourseVo> pageParam, @Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    CoursePublishVo selectCoursePublishVoById(String id);
}
