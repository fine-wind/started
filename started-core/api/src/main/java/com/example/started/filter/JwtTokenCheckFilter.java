package com.example.started.filter;

import com.example.common.v0.constant.Constant;
import com.example.started.util.JwtUtils;
import com.example.common.v0.utils.DateUtil;
import com.example.common.v0.utils.StringUtil;
import com.example.started.auth.role.user.CurrentUser;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Log4j2
@Configuration
@AllArgsConstructor
public class JwtTokenCheckFilter extends OncePerRequestFilter {

    /**
     * 每次请求都会走这个方法
     * jwt 从header 获取
     * 解析jwt
     * 设置到上下文里去
     *
     * @param request     .
     * @param response    .
     * @param filterChain .
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = "null";
        String tokenStr = request.getHeader(Constant.REQUEST.HEADER.TOKEN);
        String bearer = Constant.REQUEST.HEADER.TOKEN_PREFIX;
        if (StringUtil.isNotEmpty(tokenStr) && tokenStr.startsWith(bearer)) {
            String token = tokenStr.substring(bearer.length());
            Claims decoder = JwtUtils.decoder(token);
            if (Objects.nonNull(decoder) && Objects.nonNull(decoder.getExpiration()) && decoder.getExpiration().before(DateUtil.now())) {
                username = decoder.getSubject();
            }
        }

        CurrentUser.setUserName(username);
        log.debug("this request login user is {}", username);

        UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(username, tokenStr, new ArrayList<SimpleGrantedAuthority>(0));
        SecurityContextHolder.getContext().setAuthentication(upa);
        filterChain.doFilter(request, response);
    }
}
