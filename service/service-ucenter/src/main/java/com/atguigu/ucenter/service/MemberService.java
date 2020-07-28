package com.atguigu.ucenter.service;

import com.atguigu.commonutils.R;
import com.atguigu.ucenter.entity.MemberEntity;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-28
 */
public interface MemberService extends IService<MemberEntity> {

    R login(String mobile, String password);

    R register(RegisterVo registerVo);
}
