package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.CourseEntity;
import com.atguigu.guli.service.edu.entity.TeacherEntity;
import com.atguigu.guli.service.edu.entity.form.TeacherQueryForm;
import com.atguigu.guli.service.edu.feign.OssFileService;
import com.atguigu.guli.service.edu.mapper.CourseMapper;
import com.atguigu.guli.service.edu.mapper.TeacherMapper;
import com.atguigu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherEntity> implements TeacherService {

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public R getTeacherInfoById(String id) {
        TeacherEntity teacher = baseMapper.selectById(id);

        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseEntity::getTeacherId, id);
        List<CourseEntity> courseList = courseMapper.selectList(wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        map.put("courseList", courseList);

        return R.ok().data(map);
    }

    @Cacheable(value = "index", key = "'selectHotTeacher'")
    @Override
    public List<TeacherEntity> getHotTeacher() {
        LambdaQueryWrapper<TeacherEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(TeacherEntity::getSort).last("limit 4");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void removeAvatarById(String id) {
        //根据id获取讲师avatar 的 url
        TeacherEntity teacher = baseMapper.selectById(id);
        if (teacher != null) {
            String avatar = teacher.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                ossFileService.removeFile(avatar);
            }
        }
    }

    @Override
    public R queryPage(Long page, Long limit, TeacherQueryForm teacherQuery) {
        //显示分页查询列表
//        1、排序：按照sort字段排序
        LambdaQueryWrapper<TeacherEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(TeacherEntity::getSort);

        Page<TeacherEntity> pageParam = new Page<>(page, limit);
//        2、分页查询
        if (teacherQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return R.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());
        }

//        3、条件查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        LocalDate joinDateBegin = teacherQuery.getJoinDateBegin();
        LocalDate joinDateEnd = teacherQuery.getJoinDateEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.likeRight(TeacherEntity::getName, name);
        }

        if (level != null) {
            queryWrapper.eq(TeacherEntity::getLevel, level);
        }

        if (joinDateBegin != null) {
            queryWrapper.ge(TeacherEntity::getJoinDate, joinDateBegin);
        }

        if (joinDateEnd != null) {
            queryWrapper.le(TeacherEntity::getJoinDate, joinDateEnd);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
        return R.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());
    }

    @Override
    public R selectNameList(String key) {
        LambdaQueryWrapper<TeacherEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TeacherEntity::getName).likeRight(TeacherEntity::getName, key);
        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);
        return R.ok().data("nameList", list);
    }
}
