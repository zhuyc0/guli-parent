package com.atguigu.guli.service.ucenter.service.impl;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.common.base.result.ResultCodeEnum;
import com.atguigu.guli.common.base.util.JwtInfo;
import com.atguigu.guli.common.base.util.JwtUtils;
import com.atguigu.guli.service.base.exce.GuliException;
import com.atguigu.guli.service.ucenter.entity.MemberEntity;
import com.atguigu.guli.service.ucenter.entity.vo.LoginVo;
import com.atguigu.guli.service.ucenter.entity.vo.RegisterVo;
import com.atguigu.guli.service.ucenter.mapper.MemberMapper;
import com.atguigu.guli.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-03
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        //校验参数
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验验证码：redis
        String checkCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(checkCode)) {
            throw new GuliException(ResultCodeEnum.CODE_ERROR);
        }

        //用户是否注册：mysql
        LambdaQueryWrapper<MemberEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberEntity::getMobile, mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new GuliException(ResultCodeEnum.REGISTER_MOBLE_ERROR);
        }

        //注册
        MemberEntity member = new MemberEntity();
        member.setNickname(nickname);
        member.setMobile(mobile);
        String pass = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        member.setPassword(pass);
        member.setAvatar("https://guli-file-191125.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        member.setDisabled(false);
        baseMapper.insert(member);
    }

    @Override
    public R login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验手机号是否存在
        LambdaQueryWrapper<MemberEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberEntity::getMobile, mobile);
        MemberEntity member = baseMapper.selectOne(queryWrapper);
        if (member == null) {
            throw new GuliException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }


        String digest = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        //校验密码是否正确
        if (!Objects.equals(digest, member.getPassword())) {
            throw new GuliException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        //校验用户是否被禁用
        if (member.getDisabled()) {
            throw new GuliException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //登录：生成token
        JwtInfo info = new JwtInfo();
        info.setId(member.getId());
        info.setNickname(member.getNickname());
        info.setAvatar(member.getAvatar());

        String jwtToken = JwtUtils.getJwtToken(info);
        return R.ok().data("token", jwtToken).message("登录成功");
    }

    @Override
    public MemberEntity getByOpenid(String openid) {
        LambdaQueryWrapper<MemberEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberEntity::getOpenid, openid);
        return baseMapper.selectOne(queryWrapper);
    }
}
