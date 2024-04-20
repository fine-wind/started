package com.example.started.util;

import com.example.common.v0.constant.Constant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

/**
 * jwt 编码解码器
 */
@Log4j2
@Component
public class JwtUtils {
    @Value("${application.jwt-key}")
    private String secretKey;

    /**
     * 生成jwt
     * 使用Hs256算法，私钥使用固定密钥
     *
     * @return token
     */
    public String createJWT(String userName) {
        //指定加密算法
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        long time = Long.parseLong(Constant.PARAM_CONF.APP_SETTINGS_CONF.JWT_EXPIRATION.getValue());
        //生成JWT的时间
        long expMillis = System.currentTimeMillis() + time * 1000;
        Date exp = new Date(expMillis);
        HashMap<String, Object> claims1 = new HashMap<>();
        claims1.put(Constant.User.Login.USERNAME, userName);
        return Jwts.builder()
                 .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())) //设置签名使用的签名算法和签名使用的秘钥
                //如果有私有声明，一点要先设置这个自己创建的私有的声明，这个是给builder的claims赋值，一旦卸载标准的声明赋值之后，就是覆盖了那些标准的声明的
                .expiration(exp)
                .claims(claims1) //设置自定义负载信息
                .compact();
    }


    /**
     * 解析jwt
     *
     * @param token
     * @return
     */
    public Claims parseJWT(String token) {
        //密钥实例
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)  //设置签名的密钥
                    .build()
                    .parseSignedClaims(token); //设置要解析的jwt

            return claimsJws.getPayload();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
