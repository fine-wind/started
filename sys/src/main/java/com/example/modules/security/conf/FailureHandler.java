package com.example.modules.security.conf;

import com.alibaba.fastjson.JSON;
import com.example.common.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@Log4j2
public class FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("用户名或密码错误，访问[{}]失败", request.getRequestURI());
        response.setContentType("application/josn;charset=utf-8");
        Result<?> result = new Result<>().error("用户名或密码错误");
        // 写出去
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        // 刷新流
        writer.flush();
        writer.close();
    }
}
