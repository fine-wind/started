package com.example.started.modules.auth.server.login;

import com.example.started.auth.client.TokenDto;
import com.example.started.common.v0.utils.Result;
import com.example.started.modules.auth.server.config.CustomPasswordEncoder;
import com.example.started.modules.auth.server.config.JwtConfig;
import com.example.started.modules.auth.server.user.AuthUserEntity;
import com.example.started.modules.auth.server.user.AuthUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class LoginService {
    private final AuthUserService authUserService;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final JwtConfig jwtConfig;

    public Result<?> register(String username, String password) {
        // 检查用户名是否已存在
        if (authUserService.getByUsername(username) != null) {
            return Result.error("用户名已存在");
        }

        String encode = customPasswordEncoder.encode(password);
        AuthUserEntity entity = new AuthUserEntity();
        entity.setUsername(username);
        entity.setPassword(encode);
        entity.setStatus(100); // 注册后设为激活状态
        try {
            authUserService.save(entity);
        } catch (DuplicateKeyException e) {
            return Result.error("请更改用户名");
        }

        log.info("用户注册成功: {}", username);
        return Result.ok("注册成功");
    }

    public Result<TokenDto> login(String username, String password) {
        AuthUserEntity user = authUserService.getByUsername(username);

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
        if (!customPasswordEncoder.matches(password, user.getPassword())) {
            log.warn("登录失败: 密码错误 - {}", username);
            return Result.error("用户名或密码错误");
        }

        // 生成双Token
        TokenPair tokenPair = this.generateTokenPair(username, user.getId());

        log.info("用户登录成功: {}", username);

        TokenDto t = new TokenDto(tokenPair.getRefreshToken(), tokenPair.getAccessToken(), "Bearer");
        return Result.ok(t);
    }

    /**
     * 刷新Access Token
     */
    public Result<?> refreshToken(String refreshToken) {
        try {
            if (!validateToken(refreshToken, jwtConfig.getRefreshSecret())) {
                return Result.error(401, "Refresh Token无效");
            }

            Claims claims = getClaimsFromToken(refreshToken, jwtConfig.getRefreshSecret());
            String username = claims.getSubject();

            // 验证用户是否存在且状态正常
            AuthUserEntity user = authUserService.getByUsername(username);
            if (user == null || !"100".equals(user.getStatus())) {
                return Result.error(401, "用户状态异常");
            }

            // 生成新的Token对
            TokenPair newTokenPair = generateTokenPair(username, user.getId());

            Map<String, Object> result = new HashMap<>();
            result.put("access_token", newTokenPair.getAccessToken());
            result.put("refresh_token", newTokenPair.getRefreshToken());
            result.put("expires_in", newTokenPair.getExpiresIn());
            result.put("token_type", "Bearer");

            log.info("Token刷新成功: {}", username);
            return Result.ok(result);

        } catch (Exception e) {
            log.error("Token刷新失败: {}", e.getMessage());
            return Result.error(401, "Token刷新失败");
        }
    }

    /**
     * 生成双Token
     */
    private TokenPair generateTokenPair(String username, Long userId) {
        String accessToken = generateAccessToken(username, userId);
        String refreshToken = generateRefreshToken(username);

        return new TokenPair(accessToken, refreshToken, jwtConfig.getAccessExpiration() / 1000);
    }

    /**
     * 生成Access Token (短期)
     */
    private String generateAccessToken(String username, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getAccessExpiration());

        return Jwts.builder()
                .setSubject(username)
                .claim("type", "access")
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getAccessSecretKey())
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
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getRefreshSecretKey())
                .compact();
    }

    /**
     * 验证Token
     */
    public boolean validateToken(String jwt) {
        return validateToken(jwt, jwtConfig.getAccessSecret());
    }

    private boolean validateToken(String jwt, String secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt);
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
        return getUsernameFromToken(token, jwtConfig.getAccessSecret());
    }

    public String getUsernameFromRefreshToken(String refreshToken) {
        return getUsernameFromToken(refreshToken, jwtConfig.getRefreshSecret());
    }

    private String getUsernameFromToken(String token, String secret) {
        try {
            Claims claims = getClaimsFromToken(token, secret);
            return claims.getSubject();
        } catch (Exception e) {
            log.debug("从Token获取用户名失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从Token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token, jwtConfig.getAccessSecret());
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            log.debug("从Token获取用户ID失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取Token的Claims
     */
    private Claims getClaimsFromToken(String token, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
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