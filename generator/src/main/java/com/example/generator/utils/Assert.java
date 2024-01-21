package com.example.generator.utils;


/**
 * 断言类
 *
 * @author miemie
 * @since 2018-07-24
 */
public final class Assert {

    /**
     * 断言这个 boolean 为 true
     * 为 false 则抛出异常
     *
     * @param expression boolean 值
     * @param message    消息
     */
    public static void isTrue(boolean expression, String message, Object... params) {
        if (!expression) {
            throw new MyException(String.format(message, params));
        }
    }

    /**
     * 断言这个 boolean 为 false
     * 为 true 则抛出异常
     *
     * @param expression boolean 值
     * @param message    消息
     */
    public static void isFalse(boolean expression, String message, Object... params) {
        isTrue(!expression, message, params);
    }

}
