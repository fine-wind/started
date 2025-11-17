package com.example.started.modules.auth.server.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUserEntity> implements AuthUserService {
    public List<AuthUserEntity> ins() {
        return List.of();
    }

    @Override
    public AuthUserEntity getByUsername(String username) {
        LambdaQueryWrapper<AuthUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthUserEntity::getUsername, username);
        return baseMapper.selectOne(wrapper);
    }
}
