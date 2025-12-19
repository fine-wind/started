package com.example.started.code.gateway;

import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private final Set<String> whiteList = new HashSet<>(Arrays.asList(
            "/",
            "/api/auth/register",
            "/api/auth/login",
            ""
    ));


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /* 白名单放行*/
        if (whiteList.contains(exchange.getRequest().getURI().getPath())) {
            return chain.filter(exchange);
        }
        // 简单的token验证示例
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            // exchange.getResponse().setStatusCode(HttpStatus.UNAUT2HORIZED);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // todo 2. 提取用户信息 应用于该请求
//        String userId = claims.getSubject();
//        List<String> roles = claims.get("roles", List.class);
//        exchange.getRequest().mutate()
//                .header("X-User-Id", userId)
//                .header("X-User-Roles", String.join(",", roles))
//                .header("X-Authenticated", "true")
//                .build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}