package com.atguigu.guli.service.trade.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
public class OrderNoUtils {
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final int RANDOM_LENGTH = 3;
    /**
     * 获取订单号
     */
    public static String getOrderNo() {
        String newDate = DTF.format(LocalDateTime.now());
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < RANDOM_LENGTH; i++) {
            result.append(random.nextInt(10));
        }
        return newDate + result;
    }
}
