package com.atguigu.guli.service.statistics;

import com.atguigu.guli.service.base.config.HttpSessionConfig;
import com.atguigu.guli.service.base.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月11日
 */
@ComponentScan(basePackages = {"com.atguigu.guli"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes =
                        {RedisConfig.class, HttpSessionConfig.class})
        }
)
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@EnableScheduling
public class StatisticsApp {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApp.class, args);
    }
}
