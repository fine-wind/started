package com.example.common.annotation;

import java.lang.annotation.*;

/**
 * 字典翻译的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DictTranslationClass {

    /**
     * 是否要翻译此类， 默认需要
     */
    boolean translation() default true;

}
