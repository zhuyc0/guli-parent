package com.atguigu.ucenter;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月28日
 */
public class AppTest {
    public static void main(String[] args) {
        String digest = DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
        System.out.println("e10adc3949ba59abbe56e057f20f883e");
        System.out.println(digest);
    }
}
