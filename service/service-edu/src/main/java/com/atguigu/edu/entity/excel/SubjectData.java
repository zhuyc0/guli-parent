package com.atguigu.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月22日
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0, value = "一级分类")
    private String oneSubjectName;
    @ExcelProperty(index = 1, value = "二级分类")
    private String twoSubjectName;
}
