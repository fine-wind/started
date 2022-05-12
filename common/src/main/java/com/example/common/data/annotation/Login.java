package com.example.common.data.annotation;

import java.lang.annotation.*;

/**
 * 登录效验
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
