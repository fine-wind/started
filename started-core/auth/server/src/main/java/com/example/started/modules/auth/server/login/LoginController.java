package com.example.started.modules.auth.server.login;

import com.example.started.auth.client.LoginStatusVerification;
import com.example.started.auth.client.TokenDto;
import com.example.started.common.constant.Constant;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.server.app.AppService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private AppService appService;
    private final ValidateTokenService validateTokenService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        if (validateTokenService.validateToken(username, password)) {
            return loginService.register(username, password);
        }
        return Result.error(Constant.UniversalCode.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String username = request.get("username");
        String password = request.get("password");
        Result<TokenDto> login = loginService.login(username, password);
        if (login.isSuccess()) {
            // todo 向appId发送登录成功通知
            String appId = request.get("appId");
            String uuid = request.get("uuid");
            appService.sendToken(appId, uuid, login.getData());
        }
        LoginStatusVerification.setTokenCookie(response, login.getData());
        return login;
    }

    @PostMapping("/refresh")
    public Result<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");
        return loginService.refreshToken(refreshToken);
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");
        return loginService.logout(refreshToken);
    }

    @GetMapping("/validate")
    public Result<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Result.error(401, "Token格式错误");
        }

        String token = authHeader.substring(7);
        boolean isValid = loginService.validateToken(token);

        if (isValid) {
            String username = loginService.getUsernameFromToken(token);
            Long userId = loginService.getUserIdFromToken(token);

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