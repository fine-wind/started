package com.example.modules.security.conf;

import com.alibaba.fastjson.JSON;
import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.RedisUtils;
import com.example.common.constant.Constant;
import com.example.common.data.modules.log.entity.SysLogLoginEntity;
import com.example.common.data.modules.log.service.SysLogLoginService;
import com.example.common.modules.params.redis.SysParamsRedis;
import com.example.common.utils.CookieUtils;
import com.example.common.utils.IpUtils;
import com.example.common.utils.Result;
import com.example.common.security.JwtUtils;
import com.example.common.utils.SpringContextUtils;
import com.example.modules.log.enums.LoginOperationEnum;
import com.example.modules.log.enums.LoginStatusEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Configuration
@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    private final SysLogLoginService sysLogLoginService;
    private final SysParamsRedis paramsRedis;

    @Autowired
    public LoginSuccessHandler(SysLogLoginService sysLogLoginService, SysParamsRedis paramsRedis) {
        this.sysLogLoginService = sysLogLoginService;
        this.paramsRedis = paramsRedis;
    }

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

        long time = paramsRedis.getTokenTime();
        //   这里生成token
        String jwt = JwtUtils.encoder(principal.getUsername(), time);
        RedisUtils redisUtils = SpringContextUtils.getBean(RedisUtils.class);
        redisUtils.setCache(CacheCommonKeys.getSecurityUserToken(principal.getUsername(), jwt), "", time);    /* 放入缓存*/

        Result<?> result = new Result<>().ok(jwt);
        result.setToken(jwt);

        SysLogLoginEntity log = new SysLogLoginEntity();
        log.setOperation(LoginOperationEnum.LOGIN.value());
        log.setCreateDate(new Date());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setIp(IpUtils.getIpAddr(request));
        //登录成功
        log.setStatus(LoginStatusEnum.SUCCESS.value());
        // log.setCreator(principal.getId());
        log.setCreatorName(principal.getUsername());
        sysLogLoginService.save(log);

        /* 添加 jwtcookie*/
        CookieUtils.addCookie(response, Constant.REQUEST.HEADER.TOKEN, jwt, (int) time);

        SysResponseJSON.render(request, response, JSON.toJSONString(result));
    }

}
