package com.example.modules.security.conf;

import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.RedisUtils;
import com.example.common.constant.Constant;
import com.example.common.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录的回调
 */
@Log4j2
@Configuration
public class SysLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private RedisUtils cacheService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication == null ? null : ((UserDetails) authentication.getPrincipal()).getUsername();

        String jwt = request.getHeader(Constant.REQUEST.HEADER.TOKEN);
        cacheService.delCache(CacheCommonKeys.getSecurityUserToken(username, jwt), "");

        log.info("登出成功: {}:{}", username, jwt);

        SysResponseJSON.render(request, response, new Result<>().ok("登出成功"));
    }
}
