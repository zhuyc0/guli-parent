package com.atguigu.guli.common.base.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月03日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String nickname;
    private String avatar;
    //权限、角色等
    //不要存敏感信息
}
