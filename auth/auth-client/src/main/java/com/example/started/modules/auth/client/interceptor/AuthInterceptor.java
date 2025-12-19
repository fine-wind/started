package com.example.started.modules.auth.client.interceptor;

import com.example.started.modules.auth.client.LoginStatusVerification;
import com.example.started.modules.auth.client.annotation.RequireAuth;
import com.example.started.modules.auth.client.config.AppConfig;
import com.example.started.modules.auth.validate.TokenPair;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Component
@AllArgsConstructor
public class AuthInterceptor {

    private final AppConfig appConfig;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 只处理方法级别的处理器
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireAuth requireAuth = getRequireAuthAnnotation(handlerMethod);

        if (requireAuth == null) {
            return true;
        }

        // 检查认证
        TokenPair tokenCookie = LoginStatusVerification.getTokenCookie(request);
        // todo 检查token 是否过期
        if (Objects.isNull(tokenCookie)) {
            // 构建重定向URL
            String redirectUrl = this.buildRedirectUrl(request, requireAuth);
            response.sendRedirect(redirectUrl);
            return false;
        }

        return true;
    }

    // 在AuthInterceptor中修改检查逻辑
    private RequireAuth getRequireAuthAnnotation(HandlerMethod handlerMethod) {
        // 先检查方法上的注解
        RequireAuth methodAnnotation = handlerMethod.getMethodAnnotation(RequireAuth.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }

        // 再检查类上的注解
        RequireAuth classAnnotation = handlerMethod.getBeanType().getAnnotation(RequireAuth.class);
        if (classAnnotation != null) {
            // 检查是否在排除列表中
            String methodName = handlerMethod.getMethod().getName();
            for (String excludeMethod : classAnnotation.excludeMethods()) {
                if (excludeMethod.equals(methodName)) {
                    return null;
                }
            }
            return classAnnotation;
        }

        return null;
    }

    private String buildRedirectUrl(HttpServletRequest request, RequireAuth requireAuth) {
        // StringBuilder redirectUrl = new StringBuilder("http://localhost:80/login.html");
        StringBuilder redirectUrl = new StringBuilder(appConfig.getAuthLoginUrl());
        redirectUrl.append("?appId=").append("");

        // 添加uuid参数
        redirectUrl.append("&uuid=").append(request.getParameter("uuid"));
        String urlBack;
        if (StringUtils.hasLength(requireAuth.urlBack())) {
            urlBack = requireAuth.urlBack();
        } else {
            // 添加返回URL
            urlBack = appConfig.getDomain() + request.getParameter("urlBack");
            urlBack = Base64.getEncoder().encodeToString(urlBack.getBytes(StandardCharsets.UTF_8));
        }
        if (StringUtils.hasLength(urlBack)) {
            redirectUrl.append("&urlBack=").append(URLEncoder.encode(urlBack, StandardCharsets.UTF_8));
        } else {
            // 如果没有提供urlBack，使用当前URL
            String currentUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (queryString != null) {
                currentUrl += "?" + queryString;
            }
            redirectUrl.append("&urlBack=").append(URLEncoder.encode(currentUrl, StandardCharsets.UTF_8));
        }
        return redirectUrl.toString();
    }
}