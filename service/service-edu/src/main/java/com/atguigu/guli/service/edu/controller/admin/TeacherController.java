package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.TeacherEntity;
import com.atguigu.guli.service.edu.entity.form.TeacherQueryForm;
import com.atguigu.guli.service.edu.feign.OssFileService;
import com.atguigu.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@CrossOrigin
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OssFileService ossFileService;

    @ApiOperation("所有讲师列表")
    @GetMapping("list")
    public R listAll() {
        List<TeacherEntity> list = teacherService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师", notes = "根据ID删除讲师，逻辑删除")
    @ApiImplicitParam(value = "讲师ID", name = "id", required = true)
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id) {
        //删除讲师头像
        teacherService.removeAvatarById(id);

        //删除讲师
        teacherService.removeById(id);

        return R.ok().message("删除成功");
    }

    @ApiOperation("讲师分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "page", required = true),
            @ApiImplicitParam(value = "每页记录数", name = "limit", required = true)
    })
    @GetMapping("list/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit, TeacherQueryForm teacherQuery) {
        return teacherService.queryPage(page, limit, teacherQuery);
    }

    @ApiOperation("新增讲师")
    @PostMapping("save")
    public R save(@RequestBody TeacherEntity teacher) {
        teacherService.save(teacher);
        return R.ok().message("保存成功");
    }

    @ApiOperation("更新讲师")
    @PutMapping("update")
    public R updateById(@RequestBody TeacherEntity teacher) {
        boolean result = teacherService.updateById(teacher);
        return R.ok().message("修改成功");

    }

    @ApiOperation("根据id获取讲师信息")
    @ApiImplicitParam(value = "讲师ID", name = "id", required = true)
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id) {
        TeacherEntity teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }


    @ApiOperation(value = "根据ID列表删除讲师")
    @DeleteMapping("batch-remove")
    public R removeRows(@RequestBody List<String> idList) {
        teacherService.removeByIds(idList);
        return R.ok().message("删除成功");
    }

    @ApiOperation("根据关键字查询讲师名列表")
    @ApiImplicitParam(value = "关键字", name = "key", required = true)
    @GetMapping("list/name/{key}")
    public R selectNameListByKey( @PathVariable String key) {
        return teacherService.selectNameList(key);
    }

    @ApiOperation("测试服务调用")
    @GetMapping("test")
    public R test() {
        ossFileService.test();
        log.info("edu执行成功");
        return R.ok();
    }

    @ApiOperation("测试并发")
    @GetMapping("test_concurrent")
    public R testConcurrent() {
        log.info("test_concurrent");
        return R.ok();
    }

    @GetMapping("/message1")
    public String message1() {
        return "message1";
    }

    @GetMapping("/message2")
    public String message2() {
        return "message2";
    }
}

