package com.example.modules.security.conf;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Component
public class UsernamePasswordJSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public UsernamePasswordJSONAuthenticationFilter(AuthenticationManager authenticationManager
            , LoginSuccessHandler loginSuccessHandler
            , FailureHandler failureHandler
    ) {
        super(authenticationManager);
        setAuthenticationFailureHandler(failureHandler);
        setAuthenticationSuccessHandler(loginSuccessHandler);
        //登陆使用的路径
        setFilterProcessesUrl(Constant.User.LOGIN);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            // String body = StreamUtils.copy(request.getInputStream(), StandardCharsets.UTF_8);
            try (InputStream is = request.getInputStream()) {
                Map<String, String> authenticationBean = mapper.readValue(is, Map.class);
                String username = authenticationBean.getOrDefault(Constant.User.Login.USERNAME, "");
                String password = authenticationBean.getOrDefault(Constant.User.Login.PASSWORD, "");

                // 不是admin登录的 全都走验证码
                if (Objects.isNull(username) || Objects.isNull(password)) {
                    SysResponseJSON.render(request, response, new Result<>().error(Constant.UniversalCode.FORBIDDEN, "请输入用户名和密码"));
                    return null;
                }

                authRequest = new UsernamePasswordAuthenticationToken(username.trim(), password.trim());
            } catch (IOException ignored) {
            }
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

}
