package com.atguigu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.base.exce.GuliException;
import com.atguigu.edu.entity.SubjectEntity;
import com.atguigu.edu.entity.excel.SubjectData;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月22日
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    /**
     * 因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
     * 不能实现数据库操作
     */
    private SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        Assert.notNull(subjectService, "SubjectService 不能为空");
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //读取excel内容，一行一行进行读取
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }

        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否重复
        SubjectEntity existOneSubject = this.existSubject(subjectData.getOneSubjectName(),"0");
        //没有相同一级分类，进行添加
        if (existOneSubject == null) {
            existOneSubject = new SubjectEntity();
            existOneSubject.setParentId("0");
            //一级分类名称
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        //获取一级分类id值
        String pid = existOneSubject.getId();

        //添加二级分类
        //判断二级分类是否重复
        SubjectEntity existTwoSubject = this.existSubject(subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new SubjectEntity();
            existTwoSubject.setParentId(pid);
            //二级分类名称
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    /**
     * 判断分类不能重复添加
     *
     * @param name 名称
     * @param pid  pid
     * @return obj
     */
    private SubjectEntity existSubject(String name, String pid) {
        LambdaQueryWrapper<SubjectEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubjectEntity::getTitle, name).eq(SubjectEntity::getParentId, pid);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
