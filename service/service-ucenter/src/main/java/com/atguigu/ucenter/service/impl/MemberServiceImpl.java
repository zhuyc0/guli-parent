package com.atguigu.ucenter.service.impl;

import com.atguigu.base.exce.GuliException;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.ucenter.entity.MemberEntity;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.mapper.MemberMapper;
import com.atguigu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-28
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    public static final String SMS_KEY_PREFIX = "guli-sms:code:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R login(String mobile, String password) {
        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }

        //判断手机号是否正确
        LambdaQueryWrapper<MemberEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberEntity::getMobile, mobile);
        MemberEntity mobileMember = this.getOne(wrapper);
        //判断查询对象是否为空
        //没有这个手机号
        if (mobileMember == null) {
            throw new GuliException(20001, "登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        String digest = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!Objects.equals(digest, mobileMember.getPassword())) {
            throw new GuliException(20001, "登录失败");
        }

        //判断用户是否禁用
        if (mobileMember.getDisabled()) {
            throw new GuliException(20001, "登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return R.ok().data("token", jwtToken);
    }

    @Override
    public R register(RegisterVo registerVo) {
        //获取注册的数据
        //验证码
        String code = registerVo.getCode();
        //手机号
        String mobile = registerVo.getMobile();
        //昵称
        String nickname = registerVo.getNickname();
        //密码
        String password = registerVo.getPassword();

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001, "注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(SMS_KEY_PREFIX + mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        LambdaQueryWrapper<MemberEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberEntity::getMobile, mobile);
        int count = this.count(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "注册失败");
        }

        //数据添加数据库中
        MemberEntity member = new MemberEntity();
        member.setMobile(mobile);
        member.setNickname(nickname);
        //密码需要加密的
        String digest = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        member.setPassword(digest);
        //用户不禁用
        member.setDeleted(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
        redisTemplate.delete(SMS_KEY_PREFIX + mobile);
        return R.ok();
    }
}
