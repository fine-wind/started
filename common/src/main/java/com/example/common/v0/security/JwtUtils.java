package com.example.common.v0.security;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.StringUtil;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;
import java.util.Objects;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.*;

/**
 * jwt 编码解码器
 */
@Configurable
@Log4j2
public class JwtUtils {
    /**
     * @param username 用户名称
     * @param time     过期时间 秒
     * @return .
     */
    public static String encoder(String username, long time) {

        /* 这里乘1000 是因为jwt最小单位是毫秒*/
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
                .setNotBefore(new Date())
                // .setAudience()
                .setIssuedAt(new Date())
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }

    public static Claims decoder(String token) {
        if (StringUtil.isEmpty(token)) {
            return null;
        }
        try {
            return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
        } catch (MalformedJwtException | ExpiredJwtException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 获取加密密钥
     */
    private static String getSigningKey() {

        Constant.PARAM_CONF.KVR kvr = JWT_SECRET_KEY;
        String value = kvr.getValue();
        if (Objects.isNull(value)) {
            log.error("缓存未命中 {}", kvr.getCode());
            return kvr.getDefaultVal();
        }
        return value;
    }

}
