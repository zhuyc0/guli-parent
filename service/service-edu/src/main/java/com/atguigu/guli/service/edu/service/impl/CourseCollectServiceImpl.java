package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.CourseCollectEntity;
import com.atguigu.guli.service.edu.entity.vo.CourseCollectVo;
import com.atguigu.guli.service.edu.mapper.CourseCollectMapper;
import com.atguigu.guli.service.edu.service.CourseCollectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Service
public class CourseCollectServiceImpl extends ServiceImpl<CourseCollectMapper, CourseCollectEntity> implements CourseCollectService {

    @Override
    public boolean isCollect(String courseId, String memberId) {
        LambdaQueryWrapper<CourseCollectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseCollectEntity::getCourseId, courseId)
                .eq(CourseCollectEntity::getMemberId, memberId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public void saveCourseCollect(String courseId, String memberId) {
        //未收藏则收藏
        if (!this.isCollect(courseId, memberId)) {
            CourseCollectEntity courseCollect = new CourseCollectEntity();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memberId);
            this.save(courseCollect);
        }
    }

    @Override
    public List<CourseCollectVo> selectListByMemberId(String memberId) {
        return baseMapper.selectPageByMemberId(memberId);
    }

    @Override
    public void removeCourseCollect(String courseId, String memberId) {
        //已收藏则删除
        if (this.isCollect(courseId, memberId)) {
            LambdaQueryWrapper<CourseCollectEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CourseCollectEntity::getCourseId, courseId)
                    .eq(CourseCollectEntity::getMemberId, memberId);
            this.remove(queryWrapper);
        }
    }
}
