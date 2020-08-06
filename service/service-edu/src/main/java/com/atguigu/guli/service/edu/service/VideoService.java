package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.service.edu.entity.VideoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
public interface VideoService extends IService<VideoEntity> {

    void removeMediaVideoByChapterId(String chapterId);

    void removeMediaVideoByCourseId(String chapterId);

    void removeMediaVideoById(String id);
}
