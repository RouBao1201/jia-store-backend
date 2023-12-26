package com.roubao.config.redis;

import cn.hutool.Hutool;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Redis配置类
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
@Configuration
public class RedisConfig {
    /**
     * redis序列化配置（Jackson2JsonRedisSerializer）
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return RedisTemplate
     */
    @Bean("redisTemplate")
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key序列化方式采用String类型
        template.setKeySerializer(getJackson2JsonRedisSerializer());
        // value序列化方式采用jackson类型
        template.setValueSerializer(getJackson2JsonRedisSerializer());
        // hash的key序列化方式也是采用String类型
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value也是采用jackson类型
        template.setHashValueSerializer(getJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean("redisCacheConfiguration")
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        // 角色@Cacheable数据乱码问题
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getJackson2JsonRedisSerializer()));

        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    private Jackson2JsonRedisSerializer<Object> getJackson2JsonRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        // 设置ObjectMapper访问权限
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 记录序列化之后的数据类型，方便反序列化
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL);

        // LocalDatetime序列化，默认不兼容jdk8日期序列化
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 关闭默认的日期格式化方式，默认UTC日期格式 yyyy-MM-dd’T’HH:mm:ss.SSS
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(timeModule);
        return new Jackson2JsonRedisSerializer<>(om, Object.class);
    }
}
