package com.roubao.config.cache.token;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.roubao.utils.MD5Util;
import com.roubao.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Token缓存持有类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/11
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(TokenCacheProperties.class)
public class TokenCacheHolder {

    public static final String TOKEN_HEADER_KEY = "Authorization";

    public static boolean SUPER_ADMIN_ONLINE = false;

    private final Cache<String, Integer> tokenCache;

    private final TokenCacheProperties properties;

    public TokenCacheHolder(TokenCacheProperties properties) {
        this.properties = properties;
        log.info("初始化Token缓存池:{}", properties);
        // 初始化Token缓存池
        tokenCache = Caffeine.newBuilder()
                // 未访问时过期时间
                .expireAfterAccess(properties.getExpireAfterAccess(), TimeUnit.SECONDS)
                // 初始化容量
                .initialCapacity(properties.getInitialCapacity())
                // 最大容量
                .maximumSize(properties.getMaximumSize())
                // 移除时监听
                .removalListener((k, v, c) -> log.info("Token缓存被移除，key:{}，value:{}", k, v))
                .scheduler(Scheduler.systemScheduler())
                // 淘汰策略
                .evictionListener((k, v, c) -> log.info("Token缓存被淘汰，key:{}，value:{}", k, v))
                .build();
    }

    /**
     * 获取token缓存池
     *
     * @return Cache
     */
    public Cache<String, Integer> getTokenCache() {
        return tokenCache;
    }

    /**
     * 新增
     *
     * @param token  token令牌
     * @param userId 用户ID
     */
    public void putAtom(String token, Integer userId) {
        tokenCache.put(token, userId);
    }

    /**
     * 校验token是否正确
     *
     * @param token token
     * @return boolean
     */
    public boolean verifyToken(String token) {
        return getUserId(token) != null;
    }

    /**
     * 获取用户ID
     *
     * @param token token
     * @return 用户ID
     */
    public Integer getUserId(String token) {
        Object userId = get(token, k -> null);
        if (userId == null) {
            return null;
        }
        return (Integer) userId;
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     */
    public Integer getCurrentUserId(HttpServletRequest... request) {
        HttpServletRequest requestResult = null;
        if (request != null && request.length > 0) {
            requestResult = request[0];
        } else {
            requestResult = SessionUtil.getRequest();
        }
        String token = Objects.requireNonNull(requestResult).getHeader(TOKEN_HEADER_KEY);
        if (token == null) {
            return null;
        }
        return getUserId(token);
    }

    /**
     * 根据Token获取用户ID（线程安全，会等待其他调用get方法的线程执行）
     *
     * @param token    token令牌
     * @param function 方法
     * @return 用户ID
     */
    public Integer get(String token, Function<String, Integer> function) {
        return tokenCache.get(token, function);
    }

    /**
     * 获取缓存数据（不存在则直接返回null,不会造成线程阻塞）
     *
     * @param token token
     * @return 用户ID
     */
    public Integer getIfPresent(String token) {
        return tokenCache.getIfPresent(token);
    }

    /**
     * 清除token
     *
     * @param token token
     */
    public void invalidate(String token) {
        tokenCache.invalidate(token);
    }

    /**
     * 生成token并存入缓存
     *
     * @param userId 用户ID
     */
    public String generateTokenAndPutAtom(Integer userId) {
        String token = MD5Util.encrypt(System.currentTimeMillis() + userId + new Random().nextInt() + "");
        putAtom(token, userId);
        return token;
    }
}
