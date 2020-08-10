package com.atguigu.guli.service.trade.feign;

import com.atguigu.guli.service.base.dto.MemberDto;
import com.atguigu.guli.service.trade.feign.fallback.UcenterMemberServiceFallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
@Service
@FeignClient(value = "service-ucenter", fallback = UcenterMemberServiceFallBackImpl.class)
public interface UcenterMemberService {

    @GetMapping(value = "/inner/get-member-dto/{memberId}")
    MemberDto getMemberDtoByMemberId(@PathVariable(value = "memberId") String memberId);
}
