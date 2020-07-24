package com.atguigu.edu.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月24日
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 5113801784990184216L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    /**
     * 只用于显示
     */
    private String price;
}
