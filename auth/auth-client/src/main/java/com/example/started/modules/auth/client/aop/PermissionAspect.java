package com.example.started.modules.auth.client.aop;

import com.example.started.modules.auth.client.annotation.CheckPermission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class PermissionAspect {

//    @Autowired
//    private PermissionClient permissionClient;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Around("@annotation(checkPermission)")
//    public Object checkPermission(ProceedingJoinPoint joinPoint, CheckPermission checkPermission) throws Throwable {
//        // 1. 获取当前用户ID（从SecurityContext）
//        String userId = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//
//        // 2. 解析资源ID表达式
//        String resourceId = evaluateResourceId(checkPermission.resourceId(), joinPoint);
//
//        // 3. 构建权限检查请求
//        PermissionCheckRequest request = new PermissionCheckRequest();
//        request.setUserId(userId);
//        request.setPermissionCode(checkPermission.value());
//        request.setResourceId(resourceId);
//        request.setResourceType(checkPermission.resourceType());
//
//        // 4. 调用权限服务
//        PermissionCheckResult result = permissionClient.checkPermission(request);
//
//        // 5. 检查结果
//        if (!result.isHasPermission()) {
//            throw new AccessDeniedException(result.getMessage());
//        }
//
//        // 6. 执行业务方法
//        return joinPoint.proceed();
//    }

    private String evaluateResourceId(String expression, ProceedingJoinPoint joinPoint) {
        if (StringUtils.isEmpty(expression)) {
            return null;
        }
        // 使用Spring Expression Language解析表达式
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("args", joinPoint.getArgs());

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        return exp.getValue(context, String.class);
    }
}