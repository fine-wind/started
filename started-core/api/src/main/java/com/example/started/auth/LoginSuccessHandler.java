package com.example.started.auth;


import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.Result;
import com.example.started.config.SysResponseJSON;
import com.example.started.util.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@Configuration
@AllArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    /**
     * @param request        .
     * @param response       .
     * @param authentication .
     * @throws IOException      .
     * @throws ServletException .
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        long time = Long.parseLong(Constant.PARAM_CONF.APP_SETTINGS_CONF.JWT_EXPIRATION.getValue());
        //   这里生成token
        String jwt = JwtUtils.encoder(principal.getUsername(), time);
//        RedisUtils redisUtils = SpringContextUtils.getBean(RedisUtils.class);
//        redisUtils.hashSet(CacheCommonKeys.getSecurityUserToken(principal.getUsername()), jwt, time);    /* 放入缓存*/


//        SysLogLoginEntity log = new SysLogLoginEntity();
//        log.setOperation(LoginOperationEnum.LOGIN.value());
//        log.setCreateDate(new Date());
////        log.setIp(IpUtils.getIpAddr(request));
//        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
//        // todo 登录成功
//        log.setStatus(LoginStatusEnum.SUCCESS.value());
////        log.setCreator(principal.getId());
//        log.setCreatorName(principal.getUsername());
//        sysLogLoginService.save(log);

        Result<String> result = Result.ok("登录成功");
        result.setData(jwt);
        SysResponseJSON.render(request, response, result);
    }


}
