package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.ChapterEntity;
import com.atguigu.guli.service.edu.entity.VideoEntity;
import com.atguigu.guli.service.edu.entity.vo.ChapterVo;
import com.atguigu.guli.service.edu.entity.vo.VideoVo;
import com.atguigu.guli.service.edu.mapper.ChapterMapper;
import com.atguigu.guli.service.edu.mapper.VideoMapper;
import com.atguigu.guli.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, ChapterEntity> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<ChapterVo> nestedList(String courseId) {

        //获取章节信息列表
        LambdaQueryWrapper<ChapterEntity> chapterQueryWrapper = new LambdaQueryWrapper<>();
        chapterQueryWrapper.eq(ChapterEntity::getCourseId, courseId);
        chapterQueryWrapper.orderByAsc(ChapterEntity::getSort, ChapterEntity::getId);
        List<ChapterEntity> chapterList = baseMapper.selectList(chapterQueryWrapper);

        //获取课时信息列表
        LambdaQueryWrapper<VideoEntity> videoQueryWrapper = new LambdaQueryWrapper<>();
        videoQueryWrapper.eq(VideoEntity::getCourseId, courseId);
        videoQueryWrapper.orderByAsc(VideoEntity::getSort, VideoEntity::getId);
        List<VideoEntity> videoList = videoMapper.selectList(videoQueryWrapper);

        //组装小节
        Map<String, List<VideoVo>> collect = videoList.stream().map(x -> {
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(x, videoVo);
            return videoVo;
        }).collect(Collectors.groupingBy(VideoVo::getChapterId));

        //组装章节列表：List<ChapterVo>
        List<ChapterVo> chapterVos = chapterList.stream().map(x -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(x, chapterVo);
            return chapterVo;
        }).collect(Collectors.toList());

        chapterVos.forEach(x -> {
            List<VideoVo> videoVos = collect.get(x.getId());
            if (!CollectionUtils.isEmpty(videoVos)) {
                x.setChildren(videoVos);
            }
        });

        return chapterVos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeChapterById(String id) {
        //根据courseId删除Video(课时)
        QueryWrapper<VideoEntity> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        videoMapper.delete(videoQueryWrapper);
        //删除章节
        baseMapper.deleteById(id);
    }
}
