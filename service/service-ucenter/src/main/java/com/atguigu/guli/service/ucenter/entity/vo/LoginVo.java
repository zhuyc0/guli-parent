package com.atguigu.guli.service.ucenter.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月03日
 */
@Data
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "手机不能为空")
    @Pattern(regexp = "^[1][3,45789][0-9]{9}$", message = "手机号格式不正确")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    private String password;
}
