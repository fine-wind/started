package com.example.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认过期时长为30分钟，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 30;
    /**
     * 过期时长为1小时，单位：秒
     */
    public final static long HOUR_ONE_EXPIRE = (long) 60 * 60;
    /**
     * 过期时长为6小时，单位：秒
     */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;

    /**
     * @param key   key
     * @param value value
     * @see RedisUtils#DEFAULT_EXPIRE 缓存默认时长
     */
    public void setCache(String key, Object value) {
        this.setCache(key, value, DEFAULT_EXPIRE);
    }

    /**
     * @param key    key
     * @param value  value
     * @param expire n秒
     */
    public void setCache(String key, Object value, Long expire) {
        if (expire != null) {
            if (expire >= NOT_EXPIRE) {
                this.setCache(key, value, expire, TimeUnit.SECONDS);
            }
        } else {
            this.setCache(key, value, DEFAULT_EXPIRE);
        }
    }

    public void setCache(String key, Object value, long expire, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, expire > NOT_EXPIRE ? expire : DEFAULT_EXPIRE, timeUnit);
    }

    /**
     * 删除多个key
     *
     * @param keys keys
     */
    public void delCache(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    public <T> T get(String key, long expire) {
        T value = (T) redisTemplate.opsForValue().get(key);
        if (value != null) {
            /* 命中缓存*/
            logger.info("Hit cache by key: {}, value: {}", key, value);
        } else {
            /* 未命中缓存*/
            logger.info("Miss cache by key: {}", key);
        }
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    public <T> T get(String key) {
        return this.get(key, NOT_EXPIRE);
    }


    public <T> T getCache(String key) {
        return this.get(key);
    }

    /**
     * hash存在这个key
     */
    public boolean hashHasKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public <T> T hashGet(String key, String field) {
        return (T) redisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> hashGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public void hashMSet(String key, Map<String, Object> map) {
        hashMSet(key, map, DEFAULT_EXPIRE);
    }

    public void hashMSet(String key, Map<String, Object> map, long expire) {
        redisTemplate.opsForHash().putAll(key, map);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void hashSet(String key, String field, Object value) {
        hashSet(key, field, value, DEFAULT_EXPIRE);
    }

    public void hashSet(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void hashDelKeys(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 向列表添加一个元素
     *
     * @param key   kye
     * @param value 元素
     */
    public void listLeftPush(String key, Object value) {
        this.listLeftPush(key, value, DEFAULT_EXPIRE);
    }

    public void listLeftPush(String key, Object value, long expire) {
        redisTemplate.opsForList().leftPush(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 移除列表中最后一个原元素
     *
     * @param key key
     * @return 移除的元素
     */
    public List<Object> list(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 移除列表中最后一个原元素
     *
     * @param key key
     * @return 移除的元素
     */
    public <T> T listRightPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置为默认缓存时间
     *
     * @param key key
     */
    public void expire(String key) {
        this.expire(key, DEFAULT_EXPIRE);
    }

    /**
     * 设置缓存时间
     *
     * @param key    key
     * @param expire n秒
     */
    public void expire(String key, long expire) {
        this.expire(key, expire, TimeUnit.SECONDS);
    }

    public void expire(String key, long expire, TimeUnit timeUnit) {
        this.redisTemplate.expire(key, expire, timeUnit);
    }

    /**
     * 自增的字典
     */
    static Map<String, RedisAtomicLong> redisAtomicLongMap = new HashMap<>();

    /**
     * 自增函数 默认1小时
     *
     * @param key key
     * @return 自增值
     */
    public Long incr(String key) {
        RedisAtomicLong redisAtomicLong = redisAtomicLongMap.get(key);
        if (Objects.isNull(redisAtomicLong)) {
            redisAtomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
            redisAtomicLongMap.put(key, redisAtomicLong);
        }
        long increment = redisAtomicLong.getAndIncrement();

        if (increment == 0) {//初始设置过期时间
            redisAtomicLong.expire(HOUR_ONE_EXPIRE, TimeUnit.SECONDS);
        }
        return increment;
    }

    /**
     * 自增函数
     *
     * @param key      key
     * @param liveTime 过期时间 秒
     * @return 自增值
     */
    public Long incr(String key, long liveTime) {
        RedisAtomicLong redisAtomicLong = redisAtomicLongMap.get(key);
        if (Objects.isNull(redisAtomicLong)) {
            redisAtomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
            redisAtomicLongMap.put(key, redisAtomicLong);
        }
        long increment = redisAtomicLong.getAndIncrement();

        if (increment == 0 && liveTime > 0) {//初始设置过期时间
            redisAtomicLong.expire(liveTime, TimeUnit.SECONDS);
        }
        return increment;
    }
}
