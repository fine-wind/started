package com.example.common.v0.annotation;

import com.google.common.base.Function;

import java.io.Serializable;

/**
 * 翻译的注解
 */
public interface Ti<T> {
    String t(Serializable id, Function<T, String> a);
}
