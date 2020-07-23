package com.atguigu.edu.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.CourseDescriptionEntity;
import com.atguigu.edu.entity.CourseEntity;
import com.atguigu.edu.entity.vo.CourseInfoForm;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.atguigu.edu.mapper.CourseMapper;
import com.atguigu.edu.service.CourseDescriptionService;
import com.atguigu.edu.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-23
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveCourseInfo(CourseInfoForm courseInfo) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(courseInfo, entity);
        this.save(entity);
        String id = entity.getId();
        CourseDescriptionEntity descriptionEntity = new CourseDescriptionEntity();
        descriptionEntity.setDescription(courseInfo.getDescription()).setId(id);
        courseDescriptionService.save(descriptionEntity);
        return R.ok().data("courseId", id);
    }

    @Override
    public R pageList(CourseQuery cq) {
        Page<CourseEntity> page = new Page<>(cq.getPage(), cq.getLimit());
        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(cq.getTitle())) {
            wrapper.likeRight(CourseEntity::getTitle, cq.getTitle());
        }
        if (StringUtils.hasText(cq.getStatus())) {
            wrapper.eq(CourseEntity::getStatus, cq.getStatus());
        }
        this.page(page, wrapper);
        return R.ok().data("page", page);
    }

    @Override
    public R getCourseInfo(String courseId) {
        CourseEntity course = this.getById(courseId);
        CourseDescriptionEntity description = courseDescriptionService.getById(courseId);

        CourseInfoForm infoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, infoForm);
        infoForm.setDescription(description.getDescription());
        return R.ok().data("courseInfoVo",infoForm);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateCourseInfo(CourseInfoForm courseInfo) {
        //1 修改课程表
        CourseEntity eduCourse = new CourseEntity();
        BeanUtils.copyProperties(courseInfo,eduCourse);
        this.updateById(eduCourse);
        //2 修改描述表
        CourseDescriptionEntity description = new CourseDescriptionEntity();
        description.setId(courseInfo.getId());
        description.setDescription(courseInfo.getDescription());
        courseDescriptionService.updateById(description);
        return R.ok();
    }
}
