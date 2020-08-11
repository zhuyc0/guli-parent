package com.atguigu.guli.service.statistics.feign;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.statistics.feign.fallback.UcenterMemberServiceFallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月11日
 */
@Service
@FeignClient(value = "service-ucenter", fallback = UcenterMemberServiceFallBackImpl.class)
public interface UcenterMemberService {

    @GetMapping(value = "/admin/ucenter/member/count-register-num/{day}")
    R countRegisterNum(@PathVariable("day") String day);
}
