package com.example.modules.security.conf;

import com.example.cache.redis.RedisUtils;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.security.JwtUtils;
import com.example.common.v0.utils.CookieUtils;
import com.example.common.v0.utils.Result;
import com.example.common.v0.utils.SpringContextUtils;
import com.example.common.v0.utils.StringUtil;
import com.example.modules.security.role.SysRoleUserRedis;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@Log4j2
public class JwtTokenCheckFilter extends OncePerRequestFilter {

    @Autowired
    org.springframework.boot.autoconfigure.web.ServerProperties serverProperties;
    @Autowired
    RedisUtils cacheService;
    @Autowired
    private SysRoleUserRedis roleUserRedis;

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
        if (this.pass(method, path)) {
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
        Set<Object> roles = roleUserRedis.getRoles(username);
        ArrayList<SimpleGrantedAuthority> auths = roles.stream().map(e -> new SimpleGrantedAuthority(Objects.toString(e))).collect(Collectors.toCollection(ArrayList::new));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, 1, auths);
        SecurityContextHolder.getContext().setAuthentication(token);
        // todo 这里校验权限
        //  增加 接口和权限标识符对应关系
        SpringContextUtils.getBean(SysRoleUserRedis.class).hasRoles();
        filterChain.doFilter(request, response);
    }

    /**
     * todo xing jwt过滤需要改成以配置参数的形式控制
     */
    private boolean pass(String method, String path) {

        String contextPath = serverProperties.getServlet().getContextPath();
        if (path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }

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
