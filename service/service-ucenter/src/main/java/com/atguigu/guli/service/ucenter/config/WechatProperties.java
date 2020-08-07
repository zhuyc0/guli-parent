package com.atguigu.guli.service.ucenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月07日
 */
@Data
@Component
//注意prefix要写到最后一个 "." 符号之前
@ConfigurationProperties(prefix="wx.open")
public class WechatProperties {
    private String appId;
    private String appSecret;
    private String redirectUri;
}
