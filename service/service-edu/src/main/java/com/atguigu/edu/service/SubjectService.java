package com.atguigu.edu.service;

import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.SubjectEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-22
 */
public interface SubjectService extends IService<SubjectEntity> {

    R saveSubject(MultipartFile file);

    R tree();
}
