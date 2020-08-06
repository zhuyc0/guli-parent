package com.atguigu.guli.service.oss.config;

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
@ConfigurationProperties(prefix = "alibaba.cloud.oss")
public class OssProperties {
    private String bucketName;
    private String endpoint;
}
