package com.example.started.modules.auth.server.login;

import com.example.started.common.constant.Constant;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.server.config.CustomPasswordEncoder;
import com.example.started.modules.auth.server.sys.user.AuthUserEntity;
import com.example.started.modules.auth.server.sys.user.AuthUserService;
import com.example.started.modules.auth.validate.TokenPair;
import com.example.started.modules.auth.validate.config.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class LoginService {
    private final AuthUserService authUserService;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final JwtService jwtService;
    private static Integer registerCount = 0;

    public Result<?> register(String username, String password) {
        if (registerCount++ > 10000) {
            return Result.error("暂停注册");
        }
        if (authUserService.count() > 10000) {
            return Result.error("暂停注册");
        }
        // 检查用户名是否已存在
        if (authUserService.getByUsername(username) != null) {
            return Result.error("用户名已存在");
        }

        String encode = customPasswordEncoder.encode(password);
        AuthUserEntity entity = new AuthUserEntity();
        entity.setUsername(username);
        entity.setPassword(encode);
        entity.setStatus(100); // 注册后设为激活状态
        try {
            authUserService.save(entity);
        } catch (DuplicateKeyException e) {
            return Result.error("请更改用户名");
        }

        log.info("用户注册成功: {}", username);
        return Result.ok("注册成功");
    }

    public Result<TokenPair> login(LoginBo loginBo) {
        String username = loginBo.getUsername();
        AuthUserEntity user = authUserService.getByUsername(loginBo.getUsername());

        // 用户不存在
        if (user == null) {
            log.warn("登录失败: 用户不存在 - {}", username);
            return Result.error("用户名或密码错误");
        }

        // 账户状态检查
        if (!Objects.equals(100, user.getStatus())) {
            log.warn("登录失败: 账户状态异常 - {}, status: {}", username, user.getStatus());
            return Result.error("账户未激活或已被禁用");
        }

        // 密码验证
        if (!customPasswordEncoder.matches(loginBo.getPassword(), user.getPassword())) {
            log.warn("登录失败: 密码错误 - {}", username);
            return Result.error("用户名或密码错误");
        }

        // 生成双Token
        try {

            TokenPair tokenPair = jwtService.generateTokenPair(username, String.valueOf(user.getId()));
            TokenPair t = new TokenPair(tokenPair.getRefreshToken(), Constant.REQUEST.HEADER.TOKEN_PREFIX + tokenPair.getAccessToken(), "Bearer");
            log.info("用户登录成功: {}", username);
            return Result.ok(t);
        } catch (Exception e) {
            log.error("报错咯 {}", username, e);
        }
        return Result.error(Constant.UniversalCode.SERVER_ERROR);
    }


    /**
     * 退出登录（可以扩展为将refresh token加入黑名单）
     */
    public Result<?> logout(String refreshToken) {
        // 这里可以实现将refresh token加入黑名单的逻辑
        // 比如存储到Redis中，设置过期时间与refresh token一致
        log.info("用户退出登录");
        return Result.ok("退出成功");
    }
}