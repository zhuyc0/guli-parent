package com.atguigu.guli.service.trade.service;

import java.util.Map;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
public interface WeixinPayService {
    Map<String, Object> createNative(String orderNo, String remoteAddr);
}
