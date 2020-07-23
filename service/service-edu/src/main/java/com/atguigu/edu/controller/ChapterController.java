package com.atguigu.edu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.ChapterEntity;
import com.atguigu.edu.service.ChapterService;
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
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        //课程大纲列表,根据课程id进行查询
        return chapterService.getChapterVideoByCourseId(courseId);
    }


    @PostMapping("addChapter")
    public R addChapter(@RequestBody ChapterEntity eduChapter) {
        //添加章节
        chapterService.save(eduChapter);
        return R.ok();
    }


    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        //根据章节id查询
        ChapterEntity eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }


    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody ChapterEntity eduChapter) {
        //修改章节
        chapterService.updateById(eduChapter);
        return R.ok();
    }


    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        //删除的方法
        chapterService.deleteChapter(chapterId);
        return R.ok();
    }

}

