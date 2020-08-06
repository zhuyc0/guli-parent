package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.*;
import com.atguigu.guli.service.edu.entity.form.CourseInfoForm;
import com.atguigu.guli.service.edu.entity.form.CourseQueryForm;
import com.atguigu.guli.service.edu.entity.form.WebCourseQueryForm;
import com.atguigu.guli.service.edu.entity.vo.CoursePublishVo;
import com.atguigu.guli.service.edu.entity.vo.CourseVo;
import com.atguigu.guli.service.edu.entity.vo.WebCourseVo;
import com.atguigu.guli.service.edu.feign.OssFileService;
import com.atguigu.guli.service.edu.mapper.*;
import com.atguigu.guli.service.edu.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CourseCollectMapper courseCollectMapper;

    @Autowired
    private OssFileService ossFileService;

    @Override
    public R webSelectList(WebCourseQueryForm webCourseQuery) {
        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<>();

        //查询已发布的课程
        wrapper.eq(CourseEntity::getStatus, CourseEntity.COURSE_NORMAL);

        if (!StringUtils.isEmpty(webCourseQuery.getSubjectParentId())) {
            wrapper.eq(CourseEntity::getSubjectParentId, webCourseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(webCourseQuery.getSubjectId())) {
            wrapper.eq(CourseEntity::getSubjectId, webCourseQuery.getSubjectId());
        }

        if (!StringUtils.isEmpty(webCourseQuery.getBuyCountSort())) {
            wrapper.orderByDesc(CourseEntity::getBuyCount);
        }

        if (!StringUtils.isEmpty(webCourseQuery.getGmtCreateSort())) {
            wrapper.orderByDesc(CourseEntity::getGmtCreate);
        }

        if (!StringUtils.isEmpty(webCourseQuery.getPriceSort())) {
            if (webCourseQuery.getType() == null || webCourseQuery.getType() == 1) {
                wrapper.orderByAsc(CourseEntity::getPrice);
            } else {
                wrapper.orderByDesc(CourseEntity::getPrice);
            }
        }
        List<CourseEntity> list = baseMapper.selectList(wrapper);

        return R.ok().data("courseList", list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WebCourseVo getWebCourseById(String courseId) {
        CourseEntity course = baseMapper.selectById(courseId);
        //更新浏览数
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
        //获取课程信息
        return baseMapper.selectWebCourseVoById(courseId);
    }

    @Cacheable(value = "index", key = "'selectHotCourse'")
    @Override
    public List<CourseEntity> getHotCourse() {
        LambdaQueryWrapper<CourseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(CourseEntity::getViewCount)
                .last("limit 8");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public R getCourseInfoById(String id) {
        //根据id获取Course
        CourseEntity course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        //根据id获取CourseDescription
        CourseDescriptionEntity courseDescription = courseDescriptionMapper.selectById(id);

        //组装成CourseInfoForm
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return R.ok().data("item", courseInfoForm);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveCourseInfo(CourseInfoForm courseInfoForm) {
        //保存Course
        CourseEntity course = new CourseEntity();
        BeanUtils.copyProperties(courseInfoForm, course);
        course.setStatus(CourseEntity.COURSE_DRAFT);
        baseMapper.insert(course);

        //保存CourseDescription
        CourseDescriptionEntity courseDescription = new CourseDescriptionEntity();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.insert(courseDescription);

        return R.ok().data("courseId", course.getId()).message("保存成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourseInfoByIf(CourseInfoForm courseInfoForm) {
        //更新Course
        CourseEntity course = new CourseEntity();
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);

        //更新CourseDescription
        CourseDescriptionEntity courseDescription = new CourseDescriptionEntity();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(courseInfoForm.getId());
        courseDescriptionMapper.updateById(courseDescription);
    }

    @Override
    public R queryPage(Long page, Long limit, CourseQueryForm courseQuery) {
        //组装查询条件
        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("c.gmt_create");

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }

        //组装分页
        Page<CourseVo> pageParam = new Page<>(page, limit);

        //执行分页查询
        //只需要在mapper层传入封装好的分页组件即可，sql分页条件组装的过程由mp自动完成
        List<CourseVo> records = baseMapper.selectPageByCourseQueryVo(pageParam, queryWrapper);
        //将records设置到pageParam中
        return R.ok().data("total", pageParam.getTotal()).data("rows", records);
    }

    @Override
    public void removeCoverById(String id) {
        //根据id获取讲师avatar 的 url
        CourseEntity course = baseMapper.selectById(id);
        if (course != null) {
            String cover = course.getCover();
            if (!StringUtils.isEmpty(cover)) {
                ossFileService.removeFile(cover);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeCourseById(String id) {
        //根据courseId删除Video(课时)
        LambdaQueryWrapper<VideoEntity> videoQueryWrapper = new LambdaQueryWrapper<>();
        videoQueryWrapper.eq(VideoEntity::getCourseId, id);
        videoMapper.delete(videoQueryWrapper);

        //根据courseId删除Chapter(章节)
        LambdaQueryWrapper<ChapterEntity> chapterQueryWrapper = new LambdaQueryWrapper<>();
        chapterQueryWrapper.eq(ChapterEntity::getCourseId, id);
        chapterMapper.delete(chapterQueryWrapper);

        //根据courseId删除Comment(评论)
        LambdaQueryWrapper<CommentEntity> commentQueryWrapper = new LambdaQueryWrapper<>();
        commentQueryWrapper.eq(CommentEntity::getCourseId, id);
        commentMapper.delete(commentQueryWrapper);

        //根据courseId删除CourseCollect(课程收藏)
        LambdaQueryWrapper<CourseCollectEntity> courseCollectQueryWrapper = new LambdaQueryWrapper<>();
        courseCollectQueryWrapper.eq(CourseCollectEntity::getCourseId, id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        //根据courseId删除CourseDescription(课程详情)
        courseDescriptionMapper.deleteById(id);

        //删除课程
        baseMapper.deleteById(id);
    }

    @Override
    public R getCoursePublishVoById(String id) {
        CoursePublishVo coursePublish = baseMapper.selectCoursePublishVoById(id);
        return R.ok().data("item", coursePublish);
    }

    @Override
    public void publishCourseById(String id) {
        CourseEntity course = new CourseEntity();
        course.setId(id);
        course.setStatus(CourseEntity.COURSE_NORMAL);
        this.updateById(course);
    }
}
