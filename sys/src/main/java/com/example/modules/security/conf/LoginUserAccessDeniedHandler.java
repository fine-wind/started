package com.example.modules.security.conf;

import com.example.common.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录用户没有权限访问的处理
 */
@Log4j2
@Configuration
public class LoginUserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.debug("登录用户没有权限访问的处理");
        SysResponseJSON.render(httpServletRequest, httpServletResponse, new Result<>().error("无权限，请重新登录"));
    }
}
