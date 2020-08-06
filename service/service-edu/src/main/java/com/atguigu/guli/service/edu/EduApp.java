package com.atguigu.guli.service.edu;

import com.atguigu.guli.service.base.config.LocalDateTimeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.guli"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes =
                        {LocalDateTimeConfig.class})
        }
)
@EnableFeignClients
@EnableDiscoveryClient
public class EduApp {
    public static void main(String[] args) {
        SpringApplication.run(EduApp.class, args);
    }
}
