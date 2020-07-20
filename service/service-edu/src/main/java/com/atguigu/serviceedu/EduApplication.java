package com.atguigu.serviceedu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author <a href="zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年05月21日
 * @desc EduApplication
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.serviceedu","com.atguigu.servicebase"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
