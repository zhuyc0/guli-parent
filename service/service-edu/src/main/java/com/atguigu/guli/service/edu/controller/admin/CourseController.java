package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.form.CourseInfoForm;
import com.atguigu.guli.service.edu.entity.form.CourseQueryForm;
import com.atguigu.guli.service.edu.service.CourseService;
import com.atguigu.guli.service.edu.service.VideoService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/admin/edu/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private VideoService videoService;

    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        return courseService.saveCourseInfo(courseInfoForm);
    }

    @ApiOperation("根据ID查询课程")
    @ApiImplicitParam(value = "课程ID", name = "id", required = true)
    @GetMapping("course-info/{id}")
    public R getById(@PathVariable String id) {
        return courseService.getCourseInfoById(id);
    }

    @ApiOperation("更新课程")
    @PutMapping("update-course-info")
    public R getById(@RequestBody CourseInfoForm courseInfoForm) {
        courseService.updateCourseInfoByIf(courseInfoForm);
        return R.ok().message("修改成功");
    }

    @ApiOperation("课程分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "page", required = true),
            @ApiImplicitParam(value = "每页记录数", name = "limit", required = true)
    })
    @GetMapping("list/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit, CourseQueryForm courseQuery) {
        return courseService.queryPage(page, limit, courseQuery);
    }

    @ApiOperation("根据ID删除课程")
    @ApiImplicitParam(value = "课程ID", name = "id", required = true)
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id) {

        //删除课程视频
        videoService.removeMediaVideoByCourseId(id);

        //删除课程封面
        courseService.removeCoverById(id);

        //删除课程
        courseService.removeCourseById(id);

        return R.ok().message("删除成功");
    }

    @ApiOperation("根据ID获取课程发布信息")
    @ApiImplicitParam(value = "课程ID", name = "id", required = true)
    @GetMapping("course-publish/{id}")
    public R getCoursePublishVoById(@PathVariable String id) {
        return courseService.getCoursePublishVoById(id);
    }

    @ApiOperation("根据id发布课程")
    @ApiImplicitParam(value = "课程ID", name = "id", required = true)
    @PutMapping("publish-course/{id}")
    public R publishCourseById(@PathVariable String id) {
        courseService.publishCourseById(id);
        return R.ok().message("发布成功");
    }
}

