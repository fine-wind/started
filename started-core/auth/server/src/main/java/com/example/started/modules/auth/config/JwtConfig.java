package com.example.started.modules.auth.config;

import com.example.started.common.v0.exception.ServerException;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {
    private String accessSecret;
    private String refreshSecret;
    private long accessExpiration = 15 * 60 * 1000;      // 15分钟
    private long refreshExpiration = 7 * 24 * 60 * 60 * 1000; // 7天

    // 生成安全的密钥
    public SecretKey getAccessSecretKey() {
        return generateSecretKey(accessSecret);
    }

    public SecretKey getRefreshSecretKey() {
        return generateSecretKey(refreshSecret);
    }

    private SecretKey generateSecretKey(String secret) {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret must be configured");
        }

        // 如果密钥长度足够，直接使用
        if (secret.length() >= 64) { // 64字符 = 512位
            return Keys.hmacShaKeyFor(secret.getBytes());
        }

        throw new ServerException("JWT secret must be configured");
    }
}