package com.example.started.modules.auth.validate.filter;

import com.example.started.common.constant.Constant;
import com.example.started.modules.auth.validate.config.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasLength(jwt)) {
            try {
                if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
                    // 从 JWT 中提取用户名
                    String username = jwtService.getUsernameFromToken(jwt);

                    // 创建认证对象
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null, // 证书（密码）不需要在这里设置
                                    null// 这里设置没权限就行，后面单独处理
                            );

                    // 设置认证细节（可选，但建议）
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 将认证对象设置到 SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.trace("Set Authentication to security context for user: {}", username);
                }
            } catch (Exception ex) {
                logger.error("Could not set user authentication in security context", ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constant.REQUEST.HEADER.TOKEN);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constant.REQUEST.HEADER.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}