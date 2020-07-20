package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.TeacherEntity;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;
import com.atguigu.serviceedu.service.TeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-05-21
 */
@CrossOrigin
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R list() {
        List<TeacherEntity> list = service.list();
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @ApiImplicitParam(value = "讲师ID", name = "id", required = true, example = "100000")
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {
        service.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页讲师列表")
    @PostMapping("{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "每页记录数", required = true),
            @ApiImplicitParam(name = "page", value = "当前页码", required = true),
            @ApiImplicitParam(name = "teacherQuery", value = "查询对象")
    })
    public R pageList(@PathVariable Long page, @PathVariable Long limit,@RequestBody TeacherQuery teacherQuery) {
        Page<TeacherEntity> pageParam = new Page<>(page, limit);
        service.pageQuery(pageParam, teacherQuery);
        List<TeacherEntity> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @ApiImplicitParam(name = "teacher", value = "讲师对象", required = true)
    @PostMapping
    public R save(@RequestBody TeacherEntity teacher) {
        service.save(teacher);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @ApiImplicitParam(name = "id", value = "讲师ID", required = true)
    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        TeacherEntity teacher = service.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "讲师ID", required = true),
            @ApiImplicitParam(name = "teacher", value = "讲师对象", required = true)
    })
    @PutMapping("{id}")
    public R updateById(@PathVariable String id, @RequestBody TeacherEntity teacher) {
        teacher.setId(id);
        service.updateById(teacher);
        return R.ok();
    }
}

