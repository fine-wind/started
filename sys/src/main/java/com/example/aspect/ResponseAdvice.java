package com.example.aspect;

import com.alibaba.fastjson.JSON;
import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.RedisUtils;
import com.example.common.constant.Constant;
import com.example.common.modules.params.redis.SysParamsRedis;
import com.example.common.security.JwtUtils;
import com.example.common.utils.CookieUtils;
import com.example.common.utils.Result;
import com.example.common.utils.StringUtil;
import com.example.common.utils.Translation;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@ControllerAdvice(basePackages = "com.example")
@Log4j2
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    SysParamsRedis paramsRedis;
    @Autowired
    Translation translation;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("{} {}", returnType, converterType);
        return true;
    }


    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        long l1 = System.currentTimeMillis();
        translation.tr(body);
        long l2 = System.currentTimeMillis();

        if (body instanceof Result) {
            Result result = (Result) body;
            /* 刷新token*/
            String token = reToken(request);
            /* 设置返回的cookie*/
            HttpServletResponse res = ((ServletServerHttpResponse) response).getServletResponse();
            if (Objects.nonNull(token)) {
                String value = Constant.PARAM_CONF.APP_SETTINGS_CONF.JWT_EXPIRATION.getValue();
                CookieUtils.addCookie(res, Constant.REQUEST.HEADER.TOKEN, token, Integer.parseInt(value));
            }
            result.setToken(token);
        }
        long l3 = System.currentTimeMillis();
        /* 耗时*/
        long p1 = l2 - l1;
        /* 刷新token*/
        long p11 = l3 - l2;
        log.info("翻译 耗时{}ms reToken时间:{}ms {}返回-> {}", p1, p11, request.getURI(), JSON.toJSONString(body));
        return body;
    }

    /**
     * @return 刷新后的token
     */
    private String reToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> tokens = headers.get(Constant.REQUEST.HEADER.TOKEN);


        if (Objects.nonNull(tokens)) {

            long tokenTime = paramsRedis.getTokenTime();

            for (String t : tokens) {
                String token = null;
                String username = Constant.ROOT.toString();
                if (StringUtil.isEmpty(t) || t.trim().length() == 0) {
//                    token = JwtUtils.encoder(username, tokenTime);
                    continue;
                } else {
                    Claims decoder = JwtUtils.decoder(t);
                    if (decoder == null) {
                        continue;
                    }
                    username = decoder.getSubject();
                }

                String userTokenKey = CacheCommonKeys.getSecurityUserToken(username, t);

                Long expire = redisUtils.getExpire(userTokenKey);
                log.info("token expire: {} time: {}", expire, tokenTime);
                if (expire > tokenTime || expire < tokenTime / 5) {
                    redisUtils.expire(userTokenKey, 20);
                    redisUtils.expire(CacheCommonKeys.getSecurityRoleKey(username), tokenTime);
                    token = JwtUtils.encoder(username, tokenTime);
                    redisUtils.setCache(CacheCommonKeys.getSecurityUserToken(username, token), "", tokenTime);
                }

                return token;
            }
        }
        return null;
    }

}
