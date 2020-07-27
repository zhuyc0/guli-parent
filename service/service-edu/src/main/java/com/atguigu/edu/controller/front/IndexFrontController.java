package com.atguigu.edu.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.CourseEntity;
import com.atguigu.edu.entity.TeacherEntity;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月27日
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index() {
        //查询前8条热门课程
        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(CourseEntity::getId).last("limit 8");
        List<CourseEntity> eduList = courseService.list(wrapper);
        //查询前4条名师
        LambdaQueryWrapper<TeacherEntity> wrapperTeacher = new LambdaQueryWrapper<>();
        wrapperTeacher.orderByDesc(TeacherEntity::getId).last("limit 4");
        List<TeacherEntity> teacherList = teacherService.list(wrapperTeacher);
        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
