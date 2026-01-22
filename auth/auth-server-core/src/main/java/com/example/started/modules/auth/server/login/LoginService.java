package com.example.started.modules.auth.server.login;

import com.example.started.common.constant.Constant;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.server.sys.user.AuthUserAddBo;
import com.example.started.modules.auth.server.sys.user.AuthUserEntity;
import com.example.started.modules.auth.server.sys.user.AuthUserService;
import com.example.started.modules.auth.validate.TokenPair;
import com.example.started.modules.auth.validate.config.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class LoginService {
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Result<?> register(String username, String password) {

        AuthUserAddBo bo = new AuthUserAddBo();
        bo.setUsername(username);
        bo.setPassword(password);
        authUserService.register(null, bo);
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
        if (!passwordEncoder.matches(loginBo.getPassword(), user.getPassword())) {
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