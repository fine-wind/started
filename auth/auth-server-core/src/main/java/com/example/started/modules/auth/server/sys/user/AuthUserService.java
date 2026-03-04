package com.example.started.modules.auth.server.sys.user;

import com.example.started.modules.auth.validate.dto.TokenUserId;

import java.util.List;

/**
 * 用户业务
 *
 * @since 1.0.0
 */
public interface AuthUserService {

    AuthUserEntity getByUserId(String userId);

    AuthUserEntity getByUsername(String username);

    /**
     *
     * @param userId 当前登录id
     * @param bo     查询参数
     * @return
     */
    List<AuthUserAllVo> all(String userId, AuthUserAllBo bo);

    void register(TokenUserId tokenUserId, AuthUserAddBo bo);

    long count(String userId, AuthUserAllBo bo);
}
