package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.ChapterEntity;
import com.atguigu.guli.service.edu.entity.vo.ChapterVo;
import com.atguigu.guli.service.edu.service.ChapterService;
import com.atguigu.guli.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@CrossOrigin
@Api(tags = "章节管理")
@RestController
@RequestMapping("/admin/edu/chapter")
@Slf4j
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    @ApiOperation("新增章节")
    @PostMapping("save")
    public R save(@RequestBody ChapterEntity chapter){
         chapterService.save(chapter);
         return R.ok().message("保存成功");
    }

    @ApiOperation("根据id查询章节")
    @ApiImplicitParam(value = "章节id",name = "id",required = true)
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id){
        ChapterEntity chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    @ApiOperation("根据id修改章节")
    @PutMapping("update")
    public R updateById(@RequestBody ChapterEntity chapter){
        chapterService.updateById(chapter);
        return R.ok().message("修改成功");
    }

    @ApiOperation("根据ID删除章节")
    @ApiImplicitParam(value = "章节id",name = "id",required = true)
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id){

        //删除课程视频
        videoService.removeMediaVideoByChapterId(id);

        //删除章节
        chapterService.removeChapterById(id);

        return R.ok().message("删除成功");

    }

    @ApiOperation("嵌套章节数据列表")
    @ApiImplicitParam(value = "章节id",name = "courseId",required = true)
    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(@PathVariable String courseId){
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }
}

