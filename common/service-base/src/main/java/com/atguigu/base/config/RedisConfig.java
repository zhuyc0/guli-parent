package com.atguigu.base.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月28日
 */
@EnableCaching
@Component
public class RedisConfig extends CachingConfigurerSupport {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置第三方序列化器
        template.setDefaultSerializer(new GenericFastJsonRedisSerializer());
        return template;
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                // PARTNER_KEY 缓存管理器
                .withCacheConfiguration("banner",
                        RedisCacheConfiguration
                                .defaultCacheConfig()
                                // 过期时间
                                .entryTtl(Duration.ofDays(7))
                                // key 前缀
                                .computePrefixWith(cacheName -> "guli-banner:")
                                // 序列化器
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()))
                                .disableCachingNullValues()
                );

    }
}
