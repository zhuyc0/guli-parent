package com.atguigu.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author <a href="zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年05月21日
 * @desc EduApplication
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.atguigu.edu.feign.client")
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.edu", "com.atguigu.base"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
