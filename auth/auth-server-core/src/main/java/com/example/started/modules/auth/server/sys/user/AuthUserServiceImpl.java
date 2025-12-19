package com.example.started.modules.auth.server.sys.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.random.RandomGenerator;

/**
 * service
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUserEntity> implements AuthUserService {


    @Override
    @Transactional(readOnly = true, timeout = 5, rollbackFor = Exception.class)
    public AuthUserEntity getByUsername(String username) {
        LambdaQueryWrapper<AuthUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthUserEntity::getUsername, username);
        return baseMapper.selectOne(wrapper);
    }


}
