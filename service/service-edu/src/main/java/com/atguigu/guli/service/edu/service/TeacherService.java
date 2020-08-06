package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.TeacherEntity;
import com.atguigu.guli.service.edu.entity.form.TeacherQueryForm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
public interface TeacherService extends IService<TeacherEntity> {

    R getTeacherInfoById(String id);

    List<TeacherEntity> getHotTeacher();

    void removeAvatarById(String id);

    R queryPage(Long page, Long limit, TeacherQueryForm teacherQuery);

    R selectNameList(String key);
}
