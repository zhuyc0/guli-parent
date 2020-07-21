package com.atguigu.oss.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author zhuyc
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_teacher")
@ApiModel(value = "TeacherEntity对象", description = "讲师")
public class TeacherEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "讲师姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "讲师简介")
    @TableField("intro")
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    @TableField("career")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
