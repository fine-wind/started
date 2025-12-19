package com.example.started.modules.auth.validate.annotation;

import java.lang.annotation.*;

/**
 * 登录用户Id
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUserId {

}