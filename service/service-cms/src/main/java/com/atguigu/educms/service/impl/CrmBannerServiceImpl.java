package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBannerEntity;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBannerEntity> implements CrmBannerService {

    //查询所有banner
    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBannerEntity> selectAllBanner() {

        //根据id进行降序排列，显示排列之后前两条记录
        LambdaQueryWrapper<CrmBannerEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(CrmBannerEntity::getId);
        //last方法，拼接sql语句
        wrapper.last("limit 2");
        return this.list(wrapper);
    }
}
