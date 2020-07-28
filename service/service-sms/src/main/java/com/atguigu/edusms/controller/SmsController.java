package com.atguigu.edusms.controller;

import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.atguigu.edusms.service.SmsService;
/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月28日
 */
@RestController
@CrossOrigin
@RequestMapping("/edusms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    //发送短信的方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        return smsService.send(phone);
    }
}
