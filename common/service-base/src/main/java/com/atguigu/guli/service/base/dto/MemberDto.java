package com.atguigu.guli.service.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
@Data
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    private String id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickname;
}
