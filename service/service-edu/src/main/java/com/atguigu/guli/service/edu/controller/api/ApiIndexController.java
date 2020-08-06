package com.atguigu.guli.service.edu.controller.api;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.CourseEntity;
import com.atguigu.guli.service.edu.entity.TeacherEntity;
import com.atguigu.guli.service.edu.service.CourseService;
import com.atguigu.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@CrossOrigin
@Api(tags = "首页")
@RestController
@RequestMapping("/api/edu/index")
public class ApiIndexController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("课程和讲师的首页数据")
    @GetMapping
    public R index() {

        //查询热门课程
        List<CourseEntity> courseList = courseService.getHotCourse();

        //查询推荐讲师
        List<TeacherEntity> teacherList = teacherService.getHotTeacher();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}
