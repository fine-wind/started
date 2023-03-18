package com.example.modules.security.conf;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;

import java.io.IOException;

/**
 * 登录超时的处理
 */
@Log4j2
@Configuration
public class InvalidSessionStrategyImpl implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        log.info("用户登录超时，访问[{}]失败", request.getRequestURI());
    }
}
