package com.example.started.modules.auth.server.login;

import com.example.started.modules.auth.client.LoginStatusVerification;
import com.example.started.common.constant.Constant;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.server.app.AppService;
import com.example.started.modules.auth.validate.TokenPair;
import com.example.started.modules.auth.validate.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtService jwtService;
    private AppService appService;
    private final ValidateTokenService validateTokenService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
//        if (validateTokenService.validateToken(username, password)) {
            return loginService.register(username, password);
//        }
//        return Result.error(Constant.UniversalCode.UN400);
    }

    @PostMapping("/login")
    public Result<TokenPair> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginBo loginBo) {
        Result<TokenPair> login = loginService.login(loginBo);
        if (login.isSuccess()) {
            // todo 向appId发送登录成功通知
            String appId = loginBo.getAppId();
            String uuid = loginBo.getUuid();
            TokenPair data = login.getData();
            appService.sendToken(appId, uuid, data);
            LoginStatusVerification.setTokenCookie(request, response, data.getRefreshToken());
            data.setRefreshToken(null);
        }
        return login;
    }

    @PutMapping("/login")
    public Result<?> refreshToken(
            HttpServletRequest request,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestHeader(value = Constant.REQUEST.HEADER.TOKEN, required = false) String accessToken,
            HttpServletResponse response) {

        Result<TokenPair> tokenPairResult = jwtService.refreshToken(refreshToken, accessToken);
        if (tokenPairResult.isSuccess()) {
            TokenPair data = tokenPairResult.getData();
            if (Objects.equals(accessToken, data.getAccessToken())) {
                data.setAccessToken(null);
            }
            String newRefreshToken = data.getRefreshToken();
            if (!Objects.equals(refreshToken, newRefreshToken)) {
                LoginStatusVerification.setTokenCookie(request, response, newRefreshToken);
            }
            data.toVo();
        }
        return tokenPairResult;
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");

        return loginService.logout(refreshToken);
    }

    @GetMapping("/validate")
    public Result<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith(Constant.REQUEST.HEADER.TOKEN_PREFIX)) {
            return Result.error(401, "Token格式错误");
        }

        String token = authHeader.substring(7);
        boolean isValid = jwtService.validateToken(token);

        if (isValid) {
            String username = jwtService.getUsernameFromToken(token);
            String userId = jwtService.getUserIdFromToken(token);

            return Result.ok(Map.of(
                    "valid", true,
                    "username", username,
                    "userId", userId
            ));
        } else {
            return Result.error(401, "Token无效");
        }
    }
}