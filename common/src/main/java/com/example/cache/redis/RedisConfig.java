package com.example.cache.redis;

import com.example.cache.constant.CacheCommonKeys;
import com.example.cache.redis.serializer.JsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis配置
 */
@Data
@Slf4j
@Configuration
@EnableCaching //启用缓存
public class RedisConfig implements CachingConfigurer {
    @Autowired
    private final RedisConnectionFactory factory;

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JsonRedisSerializer<>(Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JsonRedisSerializer<>(Object.class));
        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;
    }

    /**
     * 选择redis作为默认缓存工具  申明缓存管理器，会创建一个切面（aspect）并触发Spring缓存注解的切点（pointcut）
     * 根据类或者方法所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，将数据添加到缓存之中或者从缓存中移除某个值
     *
     * @return .
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = getRedisCacheConfigurationMap();
        return RedisCacheManager.builder()
                .initialCacheNames(redisCacheConfigurationMap.keySet())
                .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .withInitialCacheConfigurations(redisCacheConfigurationMap)// 指定 key策略
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)))// 默认5分钟
                .transactionAware()
                .build();
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(5);
        //自定义设置缓存时间
        redisCacheConfigurationMap.put(CacheCommonKeys.LANGUAGE, this.getRedisCacheConfigurationWithTtl(Duration.ofMinutes(30).getSeconds()));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(long seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }
}
