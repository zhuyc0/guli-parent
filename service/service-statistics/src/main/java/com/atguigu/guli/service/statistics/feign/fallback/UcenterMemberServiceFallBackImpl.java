package com.atguigu.guli.service.statistics.feign.fallback;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.statistics.feign.UcenterMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月11日
 */
@Slf4j
@Component
public class UcenterMemberServiceFallBackImpl implements UcenterMemberService {
    @Override
    public R countRegisterNum(String day) {
        //错误日志
        log.error("熔断器被执行");
        return R.ok().data("registerNum", 0);
    }
}
