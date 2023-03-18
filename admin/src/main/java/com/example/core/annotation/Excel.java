package com.example.core.annotation;

public @interface Excel {
    String name();

    String[] replace() default {};

    String format() default "yyyy-MM-dd";
}
