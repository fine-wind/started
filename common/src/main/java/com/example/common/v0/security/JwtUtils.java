package com.example.common.v0.security;

import com.alibaba.fastjson.JSON;
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
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
                .setNotBefore(new Date())
                // .setAudience()
                .setIssuedAt(new Date())
                .setSubject(username)
                .signWith(hs256, JWT_SECRET_KEY.getValue())
                .compact();
    }

    public static Claims decoder(String token) {
        if (StringUtil.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(JWT_SECRET_KEY.getValue()).parseClaimsJws(token);
        try {
            return claimsJws.getBody();
        } catch (MalformedJwtException | ExpiredJwtException e) {
            log.warn(e.getMessage());
        }
        return null;
    }
}
