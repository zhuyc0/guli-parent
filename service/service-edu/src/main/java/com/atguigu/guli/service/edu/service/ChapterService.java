package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.service.edu.entity.ChapterEntity;
import com.atguigu.guli.service.edu.entity.vo.ChapterVo;
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
public interface ChapterService extends IService<ChapterEntity> {

    List<ChapterVo> nestedList(String courseId);

    void removeChapterById(String id);
}
