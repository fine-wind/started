package com.example.started.modules.auth.server.app;

import com.example.started.auth.client.TokenDto;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.server.config.CustomPasswordEncoder;
import com.example.started.modules.auth.server.config.JwtConfig;
import com.example.started.modules.auth.server.login.TokenPair;
import com.example.started.modules.auth.server.user.AuthUserEntity;
import com.example.started.modules.auth.server.user.AuthUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AppService {
    private final AuthUserService authUserService;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final JwtConfig jwtConfig;

    /**
     * todo 向应用服务器发送一个通知，告知持此凭据的人已经登录成功
     *
     * @param appId 应用id
     * @param uuid  用户登录凭证
     * @param data  登录结果
     */
    public void sendToken(String appId, String uuid, TokenDto data) {

    }
}