package com.example.started.modules.auth.client.annotation;

import java.lang.annotation.*;

// 放在auth-client中
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
    String value();           // 权限码

    String resourceId() default ""; // 资源ID表达式，如: #user.id

    String resourceType() default ""; // 资源类型
}
