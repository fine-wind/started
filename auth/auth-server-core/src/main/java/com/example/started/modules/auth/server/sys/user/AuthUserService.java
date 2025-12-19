package com.example.started.modules.auth.server.sys.user;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户业务
 *
 * @since 1.0.0
 */
public interface AuthUserService extends IService<AuthUserEntity> {

    AuthUserEntity getByUsername(String username);
}
