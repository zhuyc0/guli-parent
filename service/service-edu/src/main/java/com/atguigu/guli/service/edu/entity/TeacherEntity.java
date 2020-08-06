package com.atguigu.guli.service.edu.entity;

import com.atguigu.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_teacher")
@ApiModel(value="TeacherEntity对象", description="讲师")
public class TeacherEntity extends BaseEntity {

    private static final long serialVersionUID=1L;

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

    @ApiModelProperty(value = "入驻时间")
    @TableField("join_date")
    private LocalDate joinDate;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
