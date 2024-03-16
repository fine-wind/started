package com.example.started.auth.role.aspect;

import com.alibaba.fastjson.JSON;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.tr.Translation;
import com.example.common.v0.utils.Result;
import com.example.common.v0.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


import java.util.List;
import java.util.Objects;

@Log4j2
@ControllerAdvice(basePackages = "com.example")
@AllArgsConstructor
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private final Translation translation;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("ResponseAdvice 过滤方法 -> {} {}", returnType, converterType);
        return true;
    }


    @Override
    @Nullable
    public Object beforeBodyWrite(
            @Nullable Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        long l1 = System.currentTimeMillis();
        if (body instanceof Result<?> result) {
            if (result.isSuccess()) {
                translation.trRoot(result.getData());
            }
        } else {
            translation.trRoot(body);
            body = Result.ok(body);
        }
        long l2 = System.currentTimeMillis();
        /* 耗时*/
        log.info("翻译耗时{}ms {} 返回-> {}", l2 - l1, request.getURI(), JSON.toJSONString(body));
        return body;
    }

    /**
     * todo 这里刷新token 要想办法用其他的形式处理，避免被刷新
     *
     * @return 刷新后的token
     */
    private String reToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> tokens = headers.get(Constant.REQUEST.HEADER.TOKEN);

        if (Objects.nonNull(tokens)) {

            for (String t : tokens) {
                String token = null;
                String username;
                if (StringUtil.isEmpty(t) || t.trim().length() == 0) {
                    // token = JwtUtils.encoder(username, tokenTime);
                    continue;
                } else {
//                    Claims decoder = JwtUtils.decoder(t);
//                    if (decoder == null) {
//                        continue;
//                    }
//                    username = decoder.getSubject();
                }

                // String userTokenKey = CacheCommonKeys.getSecurityUserToken(username);
                // Long expire = redisUtils.getExpire(userTokenKey);
                // log.info("token expire: {} time: {}", expire, tokenTime);
                // if (expire > tokenTime || expire < tokenTime / 5) {
                //     redisUtils.expire(userTokenKey, 20);
                //     redisUtils.expire(CacheCommonKeys.getSecurityRoleKey(username), tokenTime);
                //     token = JwtUtils.encoder(username, tokenTime);
                // }
                return token;
            }
        }
        return null;
    }

}
