package com.roubao.config.token;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.roubao.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Token缓存持有类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/11
 **/
@Slf4j
@Service
@EnableConfigurationProperties(TokenCacheProperties.class)
public class TokenCacheHolder {

    public static final String TOKEN_HEADER_KEY = "Authorization";

    private final Cache<Object, Object> tokenCache;

    private final TokenCacheProperties properties;

    public TokenCacheHolder(TokenCacheProperties properties) {
        this.properties = properties;
        // 初始化Token缓存池
        tokenCache = Caffeine.newBuilder()
                .expireAfterAccess(properties.getExpire(), TimeUnit.SECONDS)
                .initialCapacity(properties.getInitialCapacity())
                .maximumSize(properties.getMaximumSize())
                .removalListener((k, v, c) -> log.info("Token缓存被移除，key:{}，value:{}", k, v))
                .build();
    }

    /**
     * 获取token缓存池
     *
     * @return Cache
     */
    public Cache<Object, Object> getTokenCache() {
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
        return getIfAbsent(token) != null;
    }

    /**
     * 获取用户ID
     *
     * @param token token
     * @return 用户ID
     */
    public Integer getUserId(String token) {
        Object userId = getIfAbsent(token);
        if (getIfAbsent(token) == null) {
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
     * 获取缓存数据（不存在则直接返回null,不会造成线程阻塞）
     *
     * @param token token
     * @return Object
     */
    public Object getIfAbsent(String token) {
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
}
