package com.atguigu.guli.service.cms.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.cms.entity.AdTypeEntity;
import com.atguigu.guli.service.cms.service.AdTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 推荐位 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-03
 */
@CrossOrigin
@Api(tags = "推荐位管理")
@RestController
@RequestMapping("/admin/cms/ad-type")
@Slf4j
public class AdTypeController {

    @Autowired
    private AdTypeService adTypeService;

    @ApiOperation("所有推荐类别列表")
    @GetMapping("list")
    public R listAll() {
        List<AdTypeEntity> list = adTypeService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation("推荐类别分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "page", required = true),
            @ApiImplicitParam(value = "每页记录数", name = "limit", required = true)
    })
    @GetMapping("list/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit) {
        Page<AdTypeEntity> pageParam = new Page<>(page, limit);
        adTypeService.page(pageParam);
        return R.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());
    }

    @ApiOperation(value = "根据ID删除推荐类别")
    @ApiImplicitParam(value = "推荐类别ID", name = "id", required = true)
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id) {
        adTypeService.removeById(id);
        return R.ok().message("删除成功");
    }

    @ApiOperation("新增推荐类别")
    @PostMapping("save")
    public R save(@RequestBody AdTypeEntity adType) {
        adTypeService.save(adType);
        return R.ok().message("保存成功");
    }

    @ApiOperation("更新推荐类别")
    @PutMapping("update")
    public R updateById(@RequestBody AdTypeEntity adType) {
        adTypeService.updateById(adType);
        return R.ok().message("修改成功");
    }

    @ApiOperation("根据id获取推荐类别信息")
    @ApiImplicitParam(value = "推荐类别ID", name = "id", required = true)
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id) {
        AdTypeEntity adType = adTypeService.getById(id);
        return R.ok().data("item", adType);
    }
}

