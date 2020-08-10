package com.atguigu.guli.service.trade.entity;

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
 * 订单
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("trade_order")
@ApiModel(value="OrderEntity对象", description="订单")
public class OrderEntity extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "订单号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty(value = "课程id")
    @TableField("course_id")
    private String courseId;

    @ApiModelProperty(value = "课程名称")
    @TableField("course_title")
    private String courseTitle;

    @ApiModelProperty(value = "课程封面")
    @TableField("course_cover")
    private String courseCover;

    @ApiModelProperty(value = "讲师名称")
    @TableField("teacher_name")
    private String teacherName;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private String memberId;

    @ApiModelProperty(value = "会员昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "会员手机")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "订单金额（分）")
    @TableField("total_fee")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝）")
    @TableField("pay_type")
    private Integer payType;

    @ApiModelProperty(value = "订单状态（0：未支付 1：已支付）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
