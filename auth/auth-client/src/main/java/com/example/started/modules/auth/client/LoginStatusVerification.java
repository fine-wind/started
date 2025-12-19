package com.example.started.modules.auth.client;

import com.example.started.modules.auth.validate.TokenPair;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/api/auth")
public class LoginStatusVerification {
    /**
     * todo 拦截所有的请求，查看其token是否有效，若无效则重定向至登录页面
     */
    public void verification() {

    }

    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String ACCESS_TOKEN = "accessToken";


    // OAuth回调处理
    @PostMapping("/callback")
    public String oauthCallback(@RequestBody Map<String, String> map) {
        // todo 记住token
        return "SUCCESS";
    }


    public static void setTokenCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        String requestURI = request.getRequestURI();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(false)
                .path(requestURI)
                .maxAge(Duration.ofDays(7))
                .sameSite("Lax") // Strict	仅同站请求; Lax 同站请求 + 安全跨站
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }


    public static TokenPair getTokenCookie(HttpServletRequest response) {
        if (Objects.isNull(response) || Objects.isNull(response.getCookies())) {
            return null;
        }
        String refreshTokenT = null;
        String accessTokenT = null;
        for (Cookie cookie : response.getCookies()) {
            if (Objects.equals(REFRESH_TOKEN, cookie.getName())) {
                refreshTokenT = cookie.getValue();
            }
            if (Objects.equals(ACCESS_TOKEN, cookie.getName())) {
                accessTokenT = cookie.getValue();
            }
        }
        if (Objects.isNull(refreshTokenT)) {
            return null;
        }
        return new TokenPair(refreshTokenT, accessTokenT, null);
    }

}
