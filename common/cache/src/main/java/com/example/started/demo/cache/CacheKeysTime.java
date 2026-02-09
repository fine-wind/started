package com.example.started.demo.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CacheKeysTime {
    /**
     * 缓存key
     */
    private String key;
    /**
     * 缓存过期时长
     */
    private int time;
}
