package com.example.common.v1.annotation;

import com.example.common.v0.data.entity.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典翻译的注解 消灭魔法值
 * 使用方式 继承 {@link com.example.common.v0.tr.TrVo}
 * 字段上添加本注解
 * 在翻译值的字段上添加{@link Ti}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TiField {

    /**
     * 某个字段需要引用哪个类的数据
     *
     * @return 获取翻译的类类型
     */
    Class<? extends BaseEntity> source();

    /**
     * 数据表里的对应列，多为主键
     */
    String key() default "id";
}
