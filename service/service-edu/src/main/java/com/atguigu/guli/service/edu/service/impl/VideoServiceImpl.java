package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.VideoEntity;
import com.atguigu.guli.service.edu.feign.VodMediaService;
import com.atguigu.guli.service.edu.mapper.VideoMapper;
import com.atguigu.guli.service.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoEntity> implements VideoService {

    @Autowired
    private VodMediaService vodMediaService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeMediaVideoByChapterId(String chapterId) {
        LambdaQueryWrapper<VideoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(VideoEntity::getVideoSourceId);
        queryWrapper.eq(VideoEntity::getChapterId, chapterId);

        List<VideoEntity> list = baseMapper.selectList(queryWrapper);
        List<String> collect = list.stream().map(VideoEntity::getVideoSourceId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return;
        }
        vodMediaService.removeVideoByIdList(collect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeMediaVideoByCourseId(String chapterId) {
        LambdaQueryWrapper<VideoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(VideoEntity::getVideoSourceId);
        queryWrapper.eq(VideoEntity::getCourseId, chapterId);

        List<VideoEntity> list = baseMapper.selectList(queryWrapper);
        List<String> collect = list.stream().map(VideoEntity::getVideoSourceId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return;
        }
        vodMediaService.removeVideoByIdList(collect);
    }

    @Override
    public void removeMediaVideoById(String id) {
        log.warn("VideoServiceImpl：video id = " + id);
        //根据videoid找到视频id
        VideoEntity video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        log.warn("VideoServiceImpl：videoSourceId= " + videoSourceId);
        vodMediaService.removeVideo(videoSourceId);
    }
}
