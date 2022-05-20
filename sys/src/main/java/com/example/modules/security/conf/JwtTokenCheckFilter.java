package com.example.modules.security.conf;

import com.example.cache.redis.RedisUtils;
import com.example.cache.constant.CacheCommonKeys;
import com.example.common.constant.Constant;
import com.example.common.utils.CookieUtils;
import com.example.common.utils.Result;
import com.example.common.utils.StringUtil;
import com.example.common.security.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.*;

@Configuration
@Log4j2
public class JwtTokenCheckFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtils cacheService;

    /**
     * 每次请求都会走这个方法
     * jwt 从header 获取
     * 解析jwt
     * 设置到上下文里去
     *
     * @param request     .
     * @param response    .
     * @param filterChain .
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String path = request.getRequestURI();
        /* 基本数据 静态文件的判断*/
        if (pass(method, path)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims decoder = null;
        String username;
        boolean noLogin;
        /* 针对get请求*/
        if (GET.name().equals(method)) {
            String cookieValueToken = CookieUtils.getCookieValue(request, Constant.REQUEST.HEADER.TOKEN);
            noLogin = StringUtil.isEmpty(cookieValueToken) || Objects.isNull(decoder = JwtUtils.decoder(cookieValueToken));
        } else {
            String tokenStr = request.getHeader(Constant.REQUEST.HEADER.TOKEN);
            noLogin = StringUtil.isEmpty(tokenStr) || Objects.isNull(decoder = JwtUtils.decoder(tokenStr));
        }
        if (noLogin) {
            SysResponseJSON.render(request, response, new Result<>().error(Constant.UniversalCode.UNAUTHORIZED, "需要重新登录"));
            log.error("token不存在或过期 需要重新登录 " + method + " " + path);
            return;
        }
        username = decoder.getSubject();
        /* 这里设置权限*/
        List<String> roles = cacheService.get(CacheCommonKeys.getSecurityRoleKey(username));
        roles = Objects.isNull(roles) ? new ArrayList<>(0) : roles;
        ArrayList<SimpleGrantedAuthority> auths = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toCollection(ArrayList::new));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, 1, auths);
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    /**
     * todo xing jwt过滤需要改成以配置参数的形式控制
     */
    private boolean pass(String method, String path) {
        boolean post = method.equals(POST.name());
        boolean pass = post && Constant.User.JOIN.equals(path);
        pass = pass || post && Constant.User.LOGIN.equals(path);

        boolean get = GET.name().equals(method);

        pass = pass || (get && path.equals("/"));
        pass = pass || (get && path.equals("/z1"));
        pass = pass || (get && path.startsWith("/robots.txt"));


        pass = pass || (get && path.endsWith(".html"));
        pass = pass || (get && path.endsWith(".js"));
        pass = pass || (get && path.endsWith(".css"));
        pass = pass || (get && path.endsWith(".jpg"));
        pass = pass || (get && path.endsWith(".png"));
        pass = pass || (get && path.endsWith(".ico"));
        pass = pass || (get && path.endsWith(".svg"));

        pass = pass || get && path.startsWith(Constant.User.WEB_SOCKET);

        return pass;
    }
}
