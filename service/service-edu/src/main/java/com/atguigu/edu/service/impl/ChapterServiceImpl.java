package com.atguigu.edu.service.impl;

import com.atguigu.base.exce.GuliException;
import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.ChapterEntity;
import com.atguigu.edu.entity.VideoEntity;
import com.atguigu.edu.entity.vo.ChapterVO;
import com.atguigu.edu.entity.vo.VideoVO;
import com.atguigu.edu.mapper.ChapterMapper;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-23
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, ChapterEntity> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public R getChapterVideoByCourseId(String courseId) {

        // 获取章节
        LambdaQueryWrapper<ChapterEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChapterEntity::getCourseId,courseId);
        List<ChapterEntity> chapterEntities = this.list(wrapper);
        if (CollectionUtils.isEmpty(chapterEntities)){
            return R.ok().data("allChapterVideo", Collections.emptyList());
        }
        List<ChapterVO> chapters = chapterEntities.stream().map(x -> new ChapterVO().setId(x.getId()).setCId(x.getCourseId()).setTitle(x.getTitle())).collect(Collectors.toList());

        // 小节
        LambdaQueryWrapper<VideoEntity> videoWrapper = new LambdaQueryWrapper<>();
        videoWrapper.eq(VideoEntity::getCourseId,courseId);
        List<VideoEntity> videoEntities = videoService.list(videoWrapper);
        if (CollectionUtils.isEmpty(videoEntities)){
            return R.ok().data("allChapterVideo", chapters);
        }

        // 章节对应小节
        Map<String, List<VideoVO>> collect = videoEntities.stream()
                .map(x -> new VideoVO().setCId(x.getCourseId()).setId(x.getId()).setTitle(x.getTitle()).setCpId(x.getChapterId()))
                .collect(Collectors.groupingBy(VideoVO::getCpId));
        chapters.forEach(x-> x.setChildren(collect.get(x.getId())));

        return R.ok().data("allChapterVideo", chapters);
    }

    @Override
    public void deleteChapter(String chapterId) {
        ////删除章节的方法
        //根据chapterid章节id 查询小节表，如果查询数据，不进行删除
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        //判断
        //查询出小节，不进行删除
        if(videoService.count(wrapper) >0) {
            throw new GuliException(20001,"存在小节,不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            this.removeById(chapterId);
        }
    }
}
