package com.example.commons.dynamic.datasource.aspect;

import com.example.commons.dynamic.datasource.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 多数据源，切面处理类
 *
 * @since 1.0.0
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataSourceAspect {

    @Pointcut("@annotation(com.example.commons.dynamic.datasource.annotation.DataSource) " +
            "|| @within(com.example.commons.dynamic.datasource.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<?> targetClass = point.getTarget().getClass();
        Method method = signature.getMethod();

        DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
        DataSource methodDataSource = method.getAnnotation(DataSource.class);
        if (targetDataSource != null || methodDataSource != null) {
            String value;
            if (methodDataSource != null) {
                value = methodDataSource.value();
            } else {
                value = targetDataSource.value();
            }

//            DynamicContextHolder.push(value);
        }

        return point.proceed();
    }
}
