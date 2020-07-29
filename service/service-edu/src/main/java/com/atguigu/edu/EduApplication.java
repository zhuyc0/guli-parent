package com.atguigu.edu;


import com.atguigu.base.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author <a href="zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年05月21日
 * @desc EduApplication
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.atguigu.edu.feign.client")
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.edu", "com.atguigu.base"},
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = RedisConfig.class)
        })
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
