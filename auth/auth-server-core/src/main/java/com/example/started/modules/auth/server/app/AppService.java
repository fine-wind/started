package com.example.started.modules.auth.server.app;

import com.example.started.modules.auth.validate.TokenPair;
import com.example.started.modules.auth.validate.config.JwtConfig;
import com.example.started.modules.auth.server.sys.user.AuthUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AppService {
    private final AuthUserService authUserService;
    private final JwtConfig jwtConfig;

    /**
     * todo 向应用服务器发送一个通知，告知持此凭据的人已经登录成功
     *
     * @param appId 应用id
     * @param uuid  用户登录凭证
     * @param data  登录结果
     */
    public void sendToken(String appId, String uuid, TokenPair data) {

    }
}