package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.SubjectEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
public interface SubjectService extends IService<SubjectEntity> {

    R nestedList();

    void batchImport(InputStream inputStream);
}
