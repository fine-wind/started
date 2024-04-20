package com.example.started.auth;


import com.example.common.v0.constant.Constant;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.utils.CaptchaUtils;
import com.example.started.config.MixHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UsernamePasswordJSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    LoginService loginService;

    public UsernamePasswordJSONAuthenticationFilter(AuthenticationManager authenticationManager, MixHandler failureHandler) {
        super(authenticationManager);
        this.setAuthenticationFailureHandler(failureHandler);
        //登陆使用的路径
        this.setFilterProcessesUrl(Constant.User.LOGIN);
        this.setUsernameParameter(Constant.User.Login.USERNAME);
        this.setPasswordParameter(Constant.User.Login.PASSWORD);
    }

    /**
     * 重写登录
     * 调用 {@link  AuthClientUserDetailsServiceImpl#loadUserByUsername(String)} 方法
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OIDC).
     * @return .
     * @throws AuthenticationException .
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            this.check(request, response);
        } catch (ServerException e) {
            throw new AccountExpiredException("");
        }

        return super.attemptAuthentication(request, response);
    }

    private void check(HttpServletRequest request, HttpServletResponse response) throws ServerException {
        String username = obtainUsername(request);
        String uuid = request.getParameter(Constant.User.Login.UUID);
        String code = request.getParameter(Constant.User.Login.CODE);
        /* 超过5次登录 情况后验证验证码 todo 验证码的5次需要可配置*/
        long aLong = loginService.login(username);

        if (aLong > 5 && !CaptchaUtils.validate(uuid, code)) {
            // SysResponseJSON.render(request, response, Result.error(Constant.UniversalCode.PRECONDITION_FAILED, "验证码过期或不正确"));
            throw new ServerException("验证码过期或不正确");
        }
    }

}
