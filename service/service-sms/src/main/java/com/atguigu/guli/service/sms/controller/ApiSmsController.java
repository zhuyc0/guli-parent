package com.atguigu.guli.service.sms.controller;

import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.common.base.result.ResultCodeEnum;
import com.atguigu.guli.common.base.util.FormUtils;
import com.atguigu.guli.common.base.util.RandomUtils;
import com.atguigu.guli.service.base.exce.GuliException;
import com.atguigu.guli.service.sms.config.SmsProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
@Slf4j
public class ApiSmsController {

    public static final String SMS_OK = "OK";

    @Autowired
    private ISmsService smsService;

    @Autowired
    private SmsProperties properties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("send/{mobile}")
    public R getCode(@PathVariable String mobile) {

        //校验手机号是否合法
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile)) {
            log.error("手机号不正确");
            throw new GuliException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(mobile);
        if (expire != null && expire > 230) {
            return R.error().message("短信发送失败,操作频繁");
        }
        //生成验证码
        String checkCode = RandomUtils.getSixBitRandom();

        //发送验证码
        boolean send = sendCheckCode(checkCode, mobile);

        if (!send) {
            return R.error().message("短信发送失败,请稍后再试");
        }
        //存储验证码到redis
        redisTemplate.opsForValue().set(mobile, checkCode, 5, TimeUnit.MINUTES);

        return R.ok().message("短信发送成功");
    }

    /**
     * 阿里云示例,未使用
     */
    public SendBatchSmsResponse batchsendCheckCode(@RequestParam String code) {
        // 组装请求对象
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        // 使用post提交
//        request.setMethod(MethodType.GET);
        // 必填:待发送手机号。支持JSON格式的批量调用，批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumberJson("[\"177********\",\"130********\"]");
        // 必填:短信签名-支持不同的号码发送不同的短信签名
        request.setSignNameJson("[\"*******\",\"*******\"]");
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("******");
        // 必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParamJson(
                "[{\"code\":\"" + code + "\"},{\"code\":\"" + code + "\"}]");
        // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCodeJson("[\"90997\",\"90998\"]");
        try {
            SendBatchSmsResponse sendSmsResponse = smsService
                    .sendSmsBatchRequest(request);
            return sendSmsResponse;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new SendBatchSmsResponse();
    }

    private boolean sendCheckCode(String code, String mobile) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(properties.getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(properties.getTemplateCode());
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("****TraceId");
        try {
            SendSmsResponse response = smsService.sendSmsRequest(request);
            String reCode = response.getCode();
            if (SMS_OK.equals(reCode)) {
                return true;
            }
            log.error("短信发送失败-阿里云错误,手机号:{},code:{},msg:{}", mobile, reCode, response.getMessage());
        } catch (ClientException e) {
            log.error("短信发送失败-系统错误,手机号:{},msg:{}", mobile, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
