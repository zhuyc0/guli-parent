package com.atguigu.guli.service.acl.service.impl;

import com.atguigu.guli.service.acl.entity.UserEntity;
import com.atguigu.guli.service.acl.mapper.UserMapper;
import com.atguigu.guli.service.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
