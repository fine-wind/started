package com.example.started.auth.client.annotation;

import java.lang.annotation.*;

/**
 * 需要登录认证的注解（支持类和方法级别）
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireAuth {

    /**
     * 强制覆盖回调地址
     */
    String urlBack() default "";

    /**
     * 是否排除某些方法（仅在类级别使用时有效）
     */
    String[] excludeMethods() default {};
}