package com.atguigu.guli.service.ucenter.service;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.ucenter.entity.MemberEntity;
import com.atguigu.guli.service.ucenter.entity.vo.LoginVo;
import com.atguigu.guli.service.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-03
 */
public interface MemberService extends IService<MemberEntity> {

    void register(RegisterVo registerVo);

    R login(LoginVo loginVo);
}
