package com.atguigu.guli.service.edu.controller.api;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.common.base.util.JwtInfo;
import com.atguigu.guli.common.base.util.JwtUtils;
import com.atguigu.guli.service.edu.entity.vo.CourseCollectVo;
import com.atguigu.guli.service.edu.service.CourseCollectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
@RestController
@RequestMapping("/api/edu/course-collect")
@Slf4j
public class ApiCourseCollectController {

    @Autowired
    private CourseCollectService courseCollectService;

    @ApiOperation(value = "判断是否收藏")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true)
    @GetMapping("auth/is-collect/{courseId}")
    public R isCollect(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean isCollect = courseCollectService.isCollect(courseId, jwtInfo.getId());
        return R.ok().data("isCollect", isCollect);
    }

    @ApiOperation(value = "收藏课程")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true)
    @PostMapping("auth/save/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        courseCollectService.saveCourseCollect(courseId, jwtInfo.getId());
        return R.ok();
    }

    @ApiOperation(value = "获取课程收藏列表")
    @GetMapping("auth/list")
    public R collectList(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<CourseCollectVo> list = courseCollectService.selectListByMemberId(jwtInfo.getId());
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "取消收藏课程")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true)
    @DeleteMapping("auth/remove/{courseId}")
    public R remove(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        courseCollectService.removeCourseCollect(courseId, jwtInfo.getId());
        return R.ok().message("已取消");
    }
}
