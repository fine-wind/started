package com.example.started.auth.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String refreshToken;
    private String accessToken;
    /**
     * Bearer: 用户登录
     */
    private String tokenType;
}
