package com.atguigu.guli.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
@Data
public class CourseCollectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 课程id
     */
    private String courseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 课时数
     */
    private Integer lessonNum;

    /**
     * 封面
     */
    private String cover;

    /**
     * 收藏时间
     */
    private String gmtCreate;

    /**
     * 讲师
     */
    private String teacherName;
}
