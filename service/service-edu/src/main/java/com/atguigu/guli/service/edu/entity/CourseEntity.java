package com.atguigu.guli.service.edu.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.atguigu.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_course")
@ApiModel(value="CourseEntity对象", description="课程")
public class CourseEntity extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 未发布
     */
    public static final String COURSE_DRAFT = "Draft";
    /**
     * 已发布
     */
    public static final String COURSE_NORMAL = "Normal";

    @ApiModelProperty(value = "课程讲师ID")
    @TableField("teacher_id")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    @TableField("subject_id")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    @TableField("subject_parent_id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    @TableField("lesson_num")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    @TableField("cover")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    @TableField("buy_count")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    @TableField("view_count")
    private Long viewCount;

    @ApiModelProperty(value = "乐观锁")
    @TableField("version")
    private Long version;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;


}
