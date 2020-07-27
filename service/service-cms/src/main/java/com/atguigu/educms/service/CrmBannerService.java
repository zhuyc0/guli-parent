package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBannerEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-27
 */
public interface CrmBannerService extends IService<CrmBannerEntity> {

    List<CrmBannerEntity> selectAllBanner();
}
