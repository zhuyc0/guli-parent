package com.atguigu.edu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.CourseEntity;
import com.atguigu.edu.entity.vo.CourseInfoForm;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.atguigu.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-23
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public R getCourseList(CourseQuery cq) {
        return courseService.pageList(cq);
    }

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoVo) {
        //添加课程基本信息的方法
        //返回添加之后课程id，为了后面添加大纲使用
        return courseService.saveCourseInfo(courseInfoVo);
    }

    @DeleteMapping("/remove/{id}")
    public R removeCourse(@PathVariable String id) {
        courseService.removeById(id);
        return R.ok();
    }


    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        //根据课程id查询课程基本信息
        return courseService.getCourseInfo(courseId);
    }


    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfo) {
        //修改课程信息
        return courseService.updateCourseInfo(courseInfo);
    }


    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        //根据课程id查询课程确认信息
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }


    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        //课程最终发布
        //修改课程状态
        CourseEntity eduCourse = new CourseEntity();
        eduCourse.setId(id);
        //设置课程发布状态
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
}

