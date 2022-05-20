package com.example.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


@Aspect
@Component
public class HttpAspect {

    @Pointcut("execution(public * com.example.api.user.*.*(..)) || execution(public * com.example.api.user.v2.*.*(..))")
    public void pointCut() {

    }

    @AfterReturning(returning = "result", pointcut = "pointCut()")
    public void afterReturning(JoinPoint joinPoint, Object result) {

        // 请求用户登录接口，往cookie中写入TOKEN
        if("userLogin".equalsIgnoreCase(joinPoint.getSignature().getName()) || "userLoginRegisterEx".equals(joinPoint.getSignature().getName())) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = Objects.requireNonNull(attributes).getResponse();
        }
    }
}
