package com.example.started.modules.auth.server.sys.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.started.modules.auth.validate.dto.TokenUserId;

import java.util.List;

/**
 * 用户业务
 *
 * @since 1.0.0
 */
public interface AuthUserService extends IService<AuthUserEntity> {

    AuthUserEntity getByUserId(String userId);
    AuthUserEntity getByUsername(String username);

    Long count(String userId, AuthUserAllBo bo);

    List<AuthUserAllVo> all(String userId, AuthUserAllBo bo);

    void register(TokenUserId tokenUserId, AuthUserAddBo bo);
}
