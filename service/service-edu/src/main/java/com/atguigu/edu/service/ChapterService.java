package com.atguigu.edu.service;

import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.ChapterEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-23
 */
public interface ChapterService extends IService<ChapterEntity> {

    R getChapterVideoByCourseId(String courseId);

    void deleteChapter(String chapterId);
}
