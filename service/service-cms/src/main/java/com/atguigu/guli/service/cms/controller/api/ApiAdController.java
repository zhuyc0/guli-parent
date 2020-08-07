package com.atguigu.guli.service.cms.controller.api;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.cms.entity.AdEntity;
import com.atguigu.guli.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月03日
 */
@Api(tags = "广告推荐")
@RestController
@RequestMapping("/api/cms/ad")
@Slf4j
public class ApiAdController {
    @Autowired
    private AdService adService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("根据推荐位id显示广告推荐")
    @ApiImplicitParam(value = "推荐位id",name = "adTypeId",required = true)
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@PathVariable String adTypeId) {
        return adService.selectByAdTypeId(adTypeId);
    }


    @PostMapping("save-test")
    public R saveAd(@RequestBody AdEntity ad) {
        redisTemplate.opsForValue().set("index::myad", ad);
        return R.ok();
    }

    @GetMapping("get-test/{key}")
    public R getAd(@PathVariable String key) {

        AdEntity ad = (AdEntity) redisTemplate.opsForValue().get(key);
        return R.ok().data("ad", ad);
    }

    @DeleteMapping("remove-test/{key}")
    public R removeAd(@PathVariable String key) {
        Boolean delete = redisTemplate.delete(key);
        System.out.println(delete);
        Boolean aBoolean = redisTemplate.hasKey(key);
        System.out.println(aBoolean);
        return R.ok();
    }
}
