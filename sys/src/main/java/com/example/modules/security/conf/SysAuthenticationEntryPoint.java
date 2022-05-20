package com.example.modules.security.conf;

import com.example.common.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Configuration
public class SysAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // log.warn("用户需要登录，访问[{}]失败，AuthenticationException={}", request.getRequestURI(), e);
        log.warn("用户需要登录，访问[{}]失败", request.getRequestURI());

        SysResponseJSON.render(request, response, new Result<>().error("需要登录"));
    }
}
