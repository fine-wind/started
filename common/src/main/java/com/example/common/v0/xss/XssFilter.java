package com.example.common.v0.xss;

import lombok.extern.log4j.Log4j2;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 */
@Log4j2
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        log.debug("xss过滤->{}", ((HttpServletRequest) request).getRequestURI());
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
    }

}
