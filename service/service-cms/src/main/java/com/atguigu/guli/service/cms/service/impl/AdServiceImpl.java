package com.atguigu.guli.service.cms.service.impl;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.common.base.result.ResultCodeEnum;
import com.atguigu.guli.service.base.exce.GuliException;
import com.atguigu.guli.service.cms.entity.AdEntity;
import com.atguigu.guli.service.cms.entity.vo.AdVo;
import com.atguigu.guli.service.cms.feign.OssFileService;
import com.atguigu.guli.service.cms.mapper.AdMapper;
import com.atguigu.guli.service.cms.service.AdService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-03
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, AdEntity> implements AdService {

    @Autowired
    private OssFileService ossFileService;

    @Override
    public R queryPage(Long page, Long limit) {
        QueryWrapper<AdVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id", "a.sort");
        Page<AdVo> pageParam = new Page<>(page, limit);
        List<AdVo> records = baseMapper.selectPageByQueryWrapper(pageParam, queryWrapper);
        return R.ok().data("total", pageParam.getTotal()).data("rows", records);
    }

    @Override
    public void removeAdImageById(String id) {
        AdEntity ad = baseMapper.selectById(id);
        if (ad != null) {
            String imagesUrl = ad.getImageUrl();
            if (!StringUtils.isEmpty(imagesUrl)) {
                //删除图片
                R r = ossFileService.removeFile(imagesUrl);
                if (r.getSuccess()) {
                    this.removeById(id);
                    return;
                }
            }
        }
        throw new GuliException(ResultCodeEnum.FILE_DELETE_ERROR);
    }

    @Cacheable(value = "index", key = "'selectByAdTypeId'")
    @Override
    public R selectByAdTypeId(String adTypeId) {
        LambdaQueryWrapper<AdEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(AdEntity::getSort, AdEntity::getId).eq(AdEntity::getTypeId, adTypeId);
        List<AdEntity> list = baseMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }
}
