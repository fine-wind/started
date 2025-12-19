package com.example.started.modules.auth.validate.config;

import com.example.started.common.constant.Constant;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.validate.TokenPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    /**
     * 刷新Access Token
     */
    public Result<TokenPair> refreshToken(String refreshToken, String accessToken) {
        if (!this.validateToken(refreshToken, jwtConfig.getRefreshSecretKey())) {
            return Result.error(Constant.UniversalCode.UNAUTHORIZED);
        }
        Date expiration = this.getExpiration(refreshToken, jwtConfig.getRefreshSecretKey());
        if (Objects.nonNull(expiration)) {
            long curr = System.currentTimeMillis();
            if (expiration.getTime() > curr && expiration.getTime() - curr < jwtConfig.getRefreshExpiration() / 3) {
                String username = this.getUsernameFromRefreshToken(refreshToken);
                String userId = this.getUserIdFromToken(accessToken);

                return Result.ok(this.generateTokenPair(username, userId));
            }
            return Result.ok(new TokenPair(refreshToken, accessToken));
        }
        return Result.error(Constant.UniversalCode.UNAUTHORIZED);
    }

    /**
     * 生成双Token
     */
    public TokenPair generateTokenPair(String username, String userId) {
        String accessToken = this.generateAccessToken(username, userId);
        String refreshToken = this.generateRefreshToken(username);

        return new TokenPair(refreshToken, accessToken, "Bearer");
    }


    /**
     * 生成Access Token (短期)
     */
    private String generateAccessToken(String username, String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getAccessExpiration());

        return Jwts.builder()
                .setSubject(username)
                .claim("type", "access")
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtConfig.getAccessSecretKey())
                .compact();
    }

    /**
     * 生成Refresh Token (长期)
     */
    private String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getRefreshExpiration());

        return Jwts.builder()
                .setSubject(username)
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtConfig.getRefreshSecretKey())
                .compact();
    }


    /**
     * 验证Token
     */
    public boolean validateToken(String jwt) {
        return this.validateToken(jwt, jwtConfig.getAccessSecretKey());
    }

    private boolean validateToken(String jwt, SecretKey secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt.trim());
            return true;
        } catch (Exception e) {
            log.debug("Token验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从Token中获取用户名
     */
    public String getUsernameFromToken(String token) {
        return getUsernameFromToken(token, jwtConfig.getAccessSecretKey());
    }

    public String getUsernameFromRefreshToken(String refreshToken) {
        return getUsernameFromToken(refreshToken, jwtConfig.getRefreshSecretKey());
    }

    private String getUsernameFromToken(String token, SecretKey secret) {
        try {
            Claims claims = getClaimsFromToken(token, secret);
            return claims.getSubject();
        } catch (Exception e) {
            log.debug("从Token获取用户名失败: {}", e.getMessage());
            return null;
        }
    }

    private Date getExpiration(String token, SecretKey secret) {
        try {
            Claims claims = getClaimsFromToken(token, secret);
            return claims.getExpiration();
        } catch (Exception e) {
            log.debug("从Token获取工期时间失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从Token中获取用户ID
     */
    public String getUserIdFromToken(String token) {
        if (token.startsWith(Constant.REQUEST.HEADER.TOKEN_PREFIX)) {
            token = token.substring(7);
        }
        try {
            Claims claims = getClaimsFromToken(token, jwtConfig.getAccessSecretKey());
            return claims.get("userId", String.class);
        } catch (Exception e) {
            log.debug("从Token获取用户ID失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取Token的Claims
     */
    private Claims getClaimsFromToken(String token, SecretKey secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}