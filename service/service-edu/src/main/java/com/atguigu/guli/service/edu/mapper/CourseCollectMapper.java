package com.atguigu.guli.service.edu.mapper;

import com.atguigu.guli.service.edu.entity.CourseCollectEntity;
import com.atguigu.guli.service.edu.entity.vo.CourseCollectVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
public interface CourseCollectMapper extends BaseMapper<CourseCollectEntity> {

    List<CourseCollectVo> selectPageByMemberId(String memberId);
}
