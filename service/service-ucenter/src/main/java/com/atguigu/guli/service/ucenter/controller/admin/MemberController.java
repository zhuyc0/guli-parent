package com.atguigu.guli.service.ucenter.controller.admin;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月11日
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping("/admin/ucenter/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "根据日期统计注册人数")
    @ApiImplicitParam(name = "day", value = "统计日期", example = "2020-08-11")
    @GetMapping(value = "count-register-num/{day}")
    public R countRegisterNum(@PathVariable String day) {
        Integer num = memberService.countRegisterNum(day);
        return R.ok().data("registerNum", num);
    }
}
