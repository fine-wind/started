package com.example.started.config;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * 多个handler处理
 */
@Log4j2
@Configuration
public class MixHandler implements
        AuthenticationFailureHandler,
        InvalidSessionStrategy,
        SessionInformationExpiredStrategy,
        AuthenticationEntryPoint,
        LogoutSuccessHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.info("出现错误，访问[{}]失败 {}", request.getRequestURI(), e.getMessage());
        if (e instanceof UsernameNotFoundException) {
            SysResponseJSON.render(request, response, Result.error(Constant.UniversalCode.UNPROCESSABLE_ENTITY, "用户名或密码错误"));
        } else {
            SysResponseJSON.render(request, response, Result.error(Constant.UniversalCode.PRECONDITION_FAILED, "服务器异常"));
        }
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        log.info("用户登录超时，访问[{}]失败", request.getRequestURI());
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent request) {
        log.info("同一账号同时登录的用户数受限的处理，访问[{}]失败", request);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.warn("用户需要登录，访问[{}]失败", request.getRequestURI());
        SysResponseJSON.render(request, response, Result.error("需要登录"));
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SysResponseJSON.render(request, response, Result.ok("登出成功"));
    }

}
