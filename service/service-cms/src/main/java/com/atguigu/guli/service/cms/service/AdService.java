package com.atguigu.guli.service.cms.service;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.cms.entity.AdEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 广告推荐 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-03
 */
public interface AdService extends IService<AdEntity> {

    R queryPage(Long page, Long limit);

    void removeAdImageById(String id);

    R selectByAdTypeId(String adTypeId);

}
