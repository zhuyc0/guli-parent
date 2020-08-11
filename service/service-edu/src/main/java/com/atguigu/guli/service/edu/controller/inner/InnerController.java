package com.atguigu.guli.service.edu.controller.inner;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.base.dto.CourseDto;
import com.atguigu.guli.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
@Api(tags = "服务调用")
@RestController
public class InnerController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("根据课程id查询课程信息")
    @ApiImplicitParam(value = "课程id", name = "courseId", required = true)
    @GetMapping("inner/get-course-dto/{courseId}")
    public CourseDto getCourseDtoById(@PathVariable String courseId) {
        return courseService.getCourseDtoById(courseId);
    }

    @ApiOperation("根据课程id更改销售量")
    @ApiImplicitParam(value = "课程id", name = "id", required = true)
    @GetMapping("inner/update-buy-count/{id}")
    public R updateBuyCountById(@PathVariable String id) {
        courseService.updateBuyCountById(id);
        return R.ok();
    }
}
