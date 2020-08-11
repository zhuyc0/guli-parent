package com.atguigu.guli.service.statistics.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.atguigu.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网站统计日数据
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("statistics_daily")
@ApiModel(value="DailyEntity对象", description="网站统计日数据")
public class DailyEntity extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "统计日期")
    @TableField("date_calculated")
    private String dateCalculated;

    @ApiModelProperty(value = "注册人数")
    @TableField("register_num")
    private Integer registerNum;

    @ApiModelProperty(value = "登录人数")
    @TableField("login_num")
    private Integer loginNum;

    @ApiModelProperty(value = "每日播放视频数")
    @TableField("video_view_num")
    private Integer videoViewNum;

    @ApiModelProperty(value = "每日新增课程数")
    @TableField("course_num")
    private Integer courseNum;


}
