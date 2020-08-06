package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.VideoEntity;
import com.atguigu.guli.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@CrossOrigin
@Api(tags = "课时管理")
@RestController
@RequestMapping("/admin/edu/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation("新增课时")
    @PostMapping("save")
    public R save(@RequestBody VideoEntity video) {
        videoService.save(video);
        return R.ok().message("保存成功");
    }

    @ApiOperation("根据id查询课时")
    @ApiImplicitParam(value = "课时id", name = "id", required = true)
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id) {
        VideoEntity video = videoService.getById(id);
        return R.ok().data("item", video);
    }

    @ApiOperation("根据id修改课时")
    @PutMapping("update")
    public R updateById(@RequestBody VideoEntity video) {
        videoService.updateById(video);
        return R.ok().message("修改成功");
    }


    @ApiOperation("根据ID删除课时")
    @ApiImplicitParam(value = "课时id", name = "id", required = true)
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id) {
        //删除视频
        videoService.removeMediaVideoById(id);
        videoService.removeById(id);
        return R.ok().message("删除成功");
    }
}

