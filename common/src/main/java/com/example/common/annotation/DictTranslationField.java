package com.example.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典翻译的注解
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictTranslationField {

    /**
     * 字典code
     */
    String dictCode() default "";

    /**
     * 哪个数据表里
     */
    String table() default "";


    /**
     * 要显示的列
     */
    String column() default "";

    /**
     * 数据表里的对应列
     */
    String key() default "";

}
