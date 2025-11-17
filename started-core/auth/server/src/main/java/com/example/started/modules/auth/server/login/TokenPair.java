package com.example.started.modules.auth.server.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPair {
    private String accessToken;
    private String refreshToken;
    private long expiresIn; // access token过期时间（秒）
}