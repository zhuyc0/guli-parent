package com.atguigu.edu.controller;


import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.excel.SubjectData;
import com.atguigu.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService subjectService;


    @GetMapping("/tree")
    public R list(){
        return subjectService.tree();
    }

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        //添加课程分类
        //获取上传过来文件，把文件内容读取出来
        //上传过来excel文件
        return subjectService.saveSubject(file);
    }

    @GetMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("课程模板", StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), SubjectData.class).head(head())
                .sheet("模板").doWrite(dataList());

    }
    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<>();
        List<String> head0 = new ArrayList<>();
        head0.add("一级分类");
        List<String> head1 = new ArrayList<>();
        head1.add("二级分类");
        list.add(head0);
        list.add(head1);
        return list;
    }
    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<>();
        list.add(data("前端开发","Vue"));
        list.add(data("前端开发","JavaScript"));
        list.add(data("前端开发","JQuery"));
        list.add(data("后端开发","Java"));
        list.add(data("后端开发","C++"));
        list.add(data("数据库设计","MySQL"));
        return list;
    }
    private List<Object> data(String one,String two){
        List<Object> data = new ArrayList<>();
        data.add(one);
        data.add(two);
        return data;
    }
}

