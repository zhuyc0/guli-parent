package com.atguigu.guli.service.trade.entity;

import com.atguigu.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 支付日志表
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("trade_pay_log")
@ApiModel(value="PayLogEntity对象", description="支付日志表")
public class PayLogEntity extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "订单号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty(value = "支付完成时间")
    @TableField("pay_time")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "支付金额（分）")
    @TableField("total_fee")
    private Long totalFee;

    @ApiModelProperty(value = "交易流水号")
    @TableField("transaction_id")
    private String transactionId;

    @ApiModelProperty(value = "交易状态")
    @TableField("trade_state")
    private String tradeState;

    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝）")
    @TableField("pay_type")
    private Integer payType;

    @ApiModelProperty(value = "其他属性")
    @TableField("attr")
    private String attr;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
