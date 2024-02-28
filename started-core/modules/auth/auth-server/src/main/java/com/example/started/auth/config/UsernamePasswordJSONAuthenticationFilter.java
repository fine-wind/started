package com.example.started.auth.config;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.CaptchaUtils;
import com.example.common.v0.utils.Result;
import com.example.common.v0.utils.StringUtil;
import com.example.started.auth.client.conf.MixHandler;
import com.example.started.auth.client.conf.SysResponseJSON;
import com.example.started.auth.modules.login.v0.service.LoginService;
import com.example.started.auth.modules.user.v1.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
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

@Component
public class UsernamePasswordJSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    LoginService loginService;

    public UsernamePasswordJSONAuthenticationFilter(AuthenticationManager authenticationManager, LoginSuccessHandler loginSuccessHandler, MixHandler failureHandler) {
        super(authenticationManager);
        setAuthenticationFailureHandler(failureHandler);
        setAuthenticationSuccessHandler(loginSuccessHandler);
        //登陆使用的路径
        this.setFilterProcessesUrl(Constant.User.LOGIN);
    }

    /**
     * 重写登录
     * 调用 {@link  UserDetailsServiceImpl#loadUserByUsername(String)} 方法
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OIDC).
     * @return .
     * @throws AuthenticationException .
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                Map<String, String> authenticationBean = mapper.readValue(is, new TypeReference<>() {
                });
                String username = authenticationBean.getOrDefault(Constant.User.Login.USERNAME, "");
                String password = authenticationBean.getOrDefault(Constant.User.Login.PASSWORD, "");
                String uuid = authenticationBean.getOrDefault(Constant.User.Login.UUID, "");
                String code = authenticationBean.getOrDefault(Constant.User.Login.CODE, "");

                if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
                    // throw new UsernameNotFoundException("请输入用户名和密码");
                    SysResponseJSON.render(request, response, Result.error(Constant.UniversalCode.FORBIDDEN, "请输入用户名或密码"));
                    return null;
                }
                long aLong = loginService.aLogin(username);
                /* 超过5次登录 情况后验证验证码 todo 验证码的5次需要可配置*/
                if (aLong > 5 && !CaptchaUtils.validate(uuid, code)) {
                    SysResponseJSON.render(request, response, Result.error(Constant.UniversalCode.PRECONDITION_FAILED, "验证码过期或不正确"));
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
