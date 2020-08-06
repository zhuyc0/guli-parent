package com.atguigu.guli.service.vod;

import com.atguigu.guli.service.base.config.HttpSessionConfig;
import com.atguigu.guli.service.base.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@ComponentScan(basePackages = {"com.atguigu.guli"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes =
                        {RedisConfig.class, HttpSessionConfig.class})
        })
@EnableDiscoveryClient
public class VodApp {
    public static void main(String[] args) {
        SpringApplication.run(VodApp.class, args);
    }
}
