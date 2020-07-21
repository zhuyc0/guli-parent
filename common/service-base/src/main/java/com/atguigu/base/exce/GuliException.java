package com.atguigu.base.exce;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年05月22日
 * @desc GuliException
 */
@ApiModel("系统异常")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {
    private static final long serialVersionUID = 3882861949821376838L;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "提示消息")
    private String msg;
}
