package com.example.common.v3.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheKeysTime {
    /**
     * 语言key
     */
    public static final String SYS_LANGUAGE = "sys:language:";

    /**
     * 验证码Key
     */
    public static final String SYS_CAPTCHA = "sys:captcha:";

    /**
     * 默认过期时长为 5分钟，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 5;

    public static final Map<String, Long> CACHE_KEY_TIME = new HashMap<>();

    static {
        CACHE_KEY_TIME.put(SYS_LANGUAGE, DEFAULT_EXPIRE);
        CACHE_KEY_TIME.put(SYS_CAPTCHA, DEFAULT_EXPIRE);
    }

}
