package com.atguigu.guli.service.edu.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.guli.service.edu.entity.SubjectEntity;
import com.atguigu.guli.service.edu.mapper.SubjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {

    private SubjectMapper subjectMapper;

    /**
     * 遍历每一行的记录
     */
    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext context) {
        log.info("解析到一条记录: {}", data);
        //处理读取出来的数据
        String levelOneTitle = data.getLevelOneTitle();
        String levelTwoTitle = data.getLevelTwoTitle();

        //判断数据是否存在
        SubjectEntity subjectLevelOne = this.getByTitle(levelOneTitle, "0");
        String parentId = null;
        if (subjectLevelOne == null) {
            //组装一级类别
            SubjectEntity subject = new SubjectEntity();
            subject.setParentId("0");
            subject.setTitle(levelOneTitle);
            // 存入数据库
            subjectMapper.insert(subject);
            parentId = subject.getId();
        } else {
            parentId = subjectLevelOne.getId();
        }

        //判断数据是否存在
        SubjectEntity subjectLevelTwo = this.getByTitle(levelTwoTitle, parentId);
        if (subjectLevelTwo == null) {
            //组装二级类别
            SubjectEntity subject = new SubjectEntity();
            subject.setTitle(levelTwoTitle);
            subject.setParentId(parentId);
            // 存入数据库
            subjectMapper.insert(subject);
        }
    }

    /**
     * 所有数据读取之后的收尾工作
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("全部数据解析完成");
    }


    /**
     * 根据分类的名称和父id查询数据是否存在
     */
    private SubjectEntity getByTitle(String title, String parentId) {
        LambdaQueryWrapper<SubjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectEntity::getTitle, title)
                .eq(SubjectEntity::getParentId, parentId);
        return subjectMapper.selectOne(queryWrapper);
    }
}
