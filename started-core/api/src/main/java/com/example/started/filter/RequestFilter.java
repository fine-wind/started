package com.example.started.filter;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.filter.WebHttpServletRequestWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Enumeration;

@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "WebRequestFilter", urlPatterns = "/*")
@Log4j2
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info(getClass() + "过滤器init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = null;

        String serverName = servletRequest.getServerName();
        try {
            StringBuilder stringBuilder = new StringBuilder("?");
            Enumeration<?> temp = request.getParameterNames();
            if (null != temp) {
                while (temp.hasMoreElements()) {
                    String en = (String) temp.nextElement();
                    String value = request.getParameter(en);
                    stringBuilder.append(en).append("=").append(value).append('&');
                }
            }
            WebHttpServletRequestWrapper web = new WebHttpServletRequestWrapper(request);
            requestWrapper = web;
            log.info("request {}{}{} userId: {} body:{}",
                    serverName,
                    request.getRequestURI(),
                    stringBuilder.substring(0, stringBuilder.length() - 1),
                    request.getHeader(Constant.REQUEST.HEADER.TOKEN),
                    web.getBody()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
        // 在chain.doFiler方法中传递新的request对象
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            try {
                filterChain.doFilter(requestWrapper, servletResponse);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void destroy() {
        log.info("destroy");
    }
}
