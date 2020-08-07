package com.atguigu.guli.service.edu.controller;

import com.atguigu.guli.common.base.result.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月06日
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok()
                .data("name", "admin")
                .data("roles", "[admin]")
                .data("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
