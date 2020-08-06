package com.atguigu.guli.service.vod.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@Data
@Component
@ConfigurationProperties("aliyun.vod")
public class VodProperties {
    private String accessKey;
    private String secretKey;
    private String templateGroupId;
    private String workflowId;
}
