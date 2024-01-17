package com.example.started.api.util.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 */
@Aspect
@Component
public class ApiDateAop {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.example.*.controller.*.*.*( ..))")
    public void pointCut() {

    }

    /**
     * 方法执行之前
     *
     * @param joinPoint 连接点
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        LOG.debug("Calling method start: {}，params: {} ", joinPoint.getSignature().toString(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "result", pointcut = "pointCut()")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        LOG.debug("Calling method end: {}，params: {} ", joinPoint.getSignature().toString(), Arrays.toString(joinPoint.getArgs()));
    }
}
