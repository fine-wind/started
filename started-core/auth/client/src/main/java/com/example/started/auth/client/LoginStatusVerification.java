package com.example.started.auth.client;

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

    public static final String refreshToken = "refreshToken";
    public static final String accessToken = "accessToken";


    // OAuth回调处理
    @PostMapping("/callback")
    public String oauthCallback(@RequestBody Map<String, String> map) {
        // todo 记住token
        return "SUCCESS";
    }


    public static void setTokenCookie(HttpServletResponse response, TokenDto token) {
        ResponseCookie refreshTokenCookie = ResponseCookie.from(refreshToken, token.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict") // 直接支持 SameSite
                .build();
        token.setRefreshToken(null);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }


    public static TokenDto getTokenCookie(HttpServletRequest response) {
        if (Objects.isNull(response) || Objects.isNull(response.getCookies())) {
            return null;
        }
        String refreshTokenT = null;
        String accessTokenT = null;
        for (Cookie cookie : response.getCookies()) {
            if (Objects.equals(refreshToken, cookie.getName())) {
                refreshTokenT = cookie.getValue();
            }
            if (Objects.equals(accessToken, cookie.getName())) {
                accessTokenT = cookie.getValue();
            }
        }
        if (Objects.isNull(refreshTokenT)) {
            return null;
        }
        return new TokenDto(refreshTokenT, accessTokenT, null);
    }

}
