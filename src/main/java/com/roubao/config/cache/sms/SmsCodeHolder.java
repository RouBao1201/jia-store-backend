package com.roubao.config.cache.sms;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 短信验证码缓存池
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/16
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(SmsCodeProperties.class)
public class SmsCodeHolder {
    private final Cache<String, String> smsCodeCache;

    private final SmsCodeProperties smsCodeProperties;

    public SmsCodeHolder(SmsCodeProperties smsCodeProperties) {
        this.smsCodeProperties = smsCodeProperties;
        log.info("初始化SmsCode缓存池:{}", smsCodeProperties);
        // 初始化短信验证码缓存池
        smsCodeCache = Caffeine.newBuilder()
                // 某个数据在多久没有被更新后，就过期
                .expireAfterWrite(smsCodeProperties.getExpireAfterWrite(), TimeUnit.SECONDS)
                // 初始化容量
                .initialCapacity(smsCodeProperties.getInitialCapacity())
                // 最大容量
                .maximumSize(smsCodeProperties.getMaximumSize())
                // 移除时监听
                .removalListener((k, v, c) -> log.info("短信验证码缓存被移除，key:{}，value:{}", k, v))
                .scheduler(Scheduler.systemScheduler())
                // 淘汰策略
                .evictionListener((k, v, c) -> log.info("短信验证码缓存被淘汰，key:{}，value:{}", k, v))
                .build();
    }


    /**
     * 设置短信验证码
     *
     * @param username 用户名
     * @param smsCode  短信验证码
     */
    public void putAtom(String username, String smsCode) {
        smsCodeCache.put(username, smsCode);
    }

    /**
     * 获取验证码（线程安全，等待其他调用get方法的线程执行完成）
     *
     * @param username 用户名
     * @param function 方法
     * @return 短信验证码
     */
    public String get(String username, Function<String, String> function) {
        return smsCodeCache.get(username, function);
    }

    /**
     * 清除验证码
     *
     * @param username 用户名
     */
    public void invalidate(String username) {
        smsCodeCache.invalidate(username);
    }
}
