package com.atguigu.guli.service.trade.service.impl;

import com.atguigu.guli.common.base.result.ResultCodeEnum;
import com.atguigu.guli.common.base.util.ExceptionUtils;
import com.atguigu.guli.common.base.util.HttpClientUtils;
import com.atguigu.guli.service.base.exce.GuliException;
import com.atguigu.guli.service.trade.entity.OrderEntity;
import com.atguigu.guli.service.trade.service.OrderService;
import com.atguigu.guli.service.trade.service.WeixinPayService;
import com.atguigu.guli.service.trade.util.WeixinPayProperties;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
@Slf4j
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeixinPayProperties weixinPayProperties;

    @Override
    public Map<String, Object> createNative(String orderNo, String remoteAddr) {
        // 根据课程订单号获取订单
        OrderEntity order = orderService.getOrderByOrderNo(orderNo);
        Assert.notNull(order, "订单不存在");

        // 调用微信api接口：统一下单（支付订单）
        HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");

        // 组装接口参数
        Map<String, String> params = new HashMap<>();
        // 关联的公众号的appid
        params.put("appid", weixinPayProperties.getAppId());
        // 商户号
        params.put("mch_id", weixinPayProperties.getPartner());
        // 生成随机字符串
        params.put("nonce_str", WXPayUtil.generateNonceStr());
        params.put("body", order.getCourseTitle());
        params.put("out_trade_no", orderNo);
        // 注意，这里必须使用字符串类型的参数（总金额：分）
        String totalFee = order.getTotalFee().intValue() + "";
        params.put("total_fee", totalFee);
        params.put("spbill_create_ip", remoteAddr);
        params.put("notify_url", weixinPayProperties.getNotifyUrl());
        params.put("trade_type", "NATIVE");

        try {
            //将参数转换成xml字符串格式：生成带有签名的xml格式字符串
            String xmlParams = WXPayUtil.generateSignedXml(params, weixinPayProperties.getPartnerKey());

            // 将参数放入请求对象的方法体
            client.setXmlParam(xmlParams);

            // 使用https形式发送
            client.setHttps(true);

            // 发送请求
            client.post();

            // 得到响应结果
            String resultXml = client.getContent();

            // 将xml响应结果转成map对象
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);

            // 错误处理
            if ("FAIL".equals(resultMap.get("return_code")) || "FAIL".equals(resultMap.get("result_code"))) {
                log.error("微信支付统一下单错误 - "
                        + "return_code: " + resultMap.get("return_code")
                        + "return_msg: " + resultMap.get("return_msg")
                        + "result_code: " + resultMap.get("result_code")
                        + "err_code: " + resultMap.get("err_code")
                        + "err_code_des: " + resultMap.get("err_code_des"));
                throw new GuliException(ResultCodeEnum.PAY_UNIFIEDORDER_ERROR);
            }

            // 组装需要的内容
            Map<String, Object> map = new HashMap<>();

            // 响应码
            map.put("result_code", resultMap.get("result_code"));

            // 生成二维码的url
            map.put("code_url", resultMap.get("code_url"));

            // 课程id
            map.put("course_id", order.getCourseId());

            // 订单总金额
            map.put("total_fee", order.getTotalFee());

            // 订单号
            map.put("out_trade_no", orderNo);

            return map;
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.PAY_UNIFIEDORDER_ERROR);
        }
    }
}
