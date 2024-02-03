package com.example.started.util;

import com.example.common.v0.utils.StringUtil;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.*;

/**
 * jwt 编码解码器
 */
@Configurable
public class JwtUtils {
    // todo 添加签发单位等信息，并且从这里将jwt的代码抽象出来

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

        JwtParserBuilder parser = Jwts.parser();
        JwtParserBuilder jwtParser = parser.setSigningKey(JWT_SECRET_KEY.getValue());
        try {
            Jws<Claims> claimsJws = jwtParser.build().parseSignedClaims(token);
            return claimsJws.getBody();
        } catch (MalformedJwtException | ExpiredJwtException ignored) {
            return null;
        }
    }
}
