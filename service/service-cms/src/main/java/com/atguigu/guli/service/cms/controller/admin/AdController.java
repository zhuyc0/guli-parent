package com.atguigu.guli.service.cms.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.cms.entity.AdEntity;
import com.atguigu.guli.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 广告推荐 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-03
 */
@CrossOrigin
@Api(tags = "广告推荐管理")
@RestController
@RequestMapping("/admin/cms/ad")
@Slf4j
public class AdController {

    @Autowired
    private AdService adService;

    @ApiOperation("新增推荐")
    @PostMapping("save")
    public R save(@RequestBody AdEntity ad) {
        adService.save(ad);
        return R.ok().message("保存成功");
    }

    @ApiOperation("更新推荐")
    @PutMapping("update")
    public R updateById(@RequestBody AdEntity ad) {
        adService.updateById(ad);
        return R.ok().message("修改成功");
    }

    @ApiOperation("根据id获取推荐信息")
    @ApiImplicitParam(value = "推荐ID", name = "id", required = true)
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id) {
        AdEntity ad = adService.getById(id);
        return R.ok().data("item", ad);
    }

    @ApiOperation("推荐分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "page", required = true),
            @ApiImplicitParam(value = "每页记录数", name = "limit", required = true)
    })
    @GetMapping("list/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit) {
        return adService.queryPage(page, limit);
    }

    @ApiOperation(value = "根据ID删除推荐")
    @ApiImplicitParam(value = "推荐ID", name = "id", required = true)
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id) {
        //删除图片
        adService.removeAdImageById(id);
        return R.ok().message("删除成功");
    }
}

