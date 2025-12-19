package com.example.started.modules.auth.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenPair {
    private String refreshToken;
    private String accessToken;
    /**
     * Bearer: jwt 用户登录令牌
     */
    private String tokenType;


    public TokenPair(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    public TokenPair toVo() {
        this.setRefreshToken(null);
        return this;
    }
}