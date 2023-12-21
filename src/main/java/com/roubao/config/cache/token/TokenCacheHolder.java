package com.roubao.config.cache.token;

import com.roubao.common.constants.RedisKey;
import com.roubao.helper.RedisHelper;
import com.roubao.util.MD5Utils;
import com.roubao.util.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Token缓存持有类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/11
 **/
@Slf4j
public class TokenCacheHolder {

    public static final String TOKEN_HEADER_KEY = "Authorization";

    public static final String BEARER_HEADER = "Bearer ";

    /**
     * 生成token并缓存
     *
     * @param userId 用户ID
     * @return token
     */
    public static String generateTokenAndCache(Integer userId) {
        String token = MD5Utils.encrypt(System.currentTimeMillis() + userId + new Random().nextInt() + "");
        RedisHelper.set(RedisKey.PREFIX_USER_TOKEN + token, userId, 30, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 获取当前登录用户ID
     *
     * @param token token
     * @return 用户ID
     */
    public static Integer getCurrentUserId(HttpServletRequest... request) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        return RedisHelper.get(RedisKey.PREFIX_USER_TOKEN + token, Integer.class);
    }

    /**
     * 下线当前用户缓存
     *
     * @param token token
     * @return 是否成功
     */
    public static boolean cleanCurrentUserCache(HttpServletRequest... request) {
        String token = getToken(request);
        if (token == null) {
            return false;
        }
        return RedisHelper.delete(RedisKey.PREFIX_USER_TOKEN + token);
    }

    /**
     * 判断是否为当前登录用户
     *
     * @param userId 用户ID
     * @return 是否当前登录用户
     */
    public static boolean isCurrentLoginUser(Integer userId, HttpServletRequest... request) {
        String token = getToken(request);
        if (token == null) {
            return false;
        }
        Integer cacheUserId = RedisHelper.get(RedisKey.PREFIX_USER_TOKEN + token, Integer.class);
        return Objects.equals(userId, cacheUserId);
    }

    /**
     * 续期token有效期
     *
     * @param key      键
     * @param time     时间
     * @param timeUnit 时间单位
     * @return 是否成功
     */
    public static boolean renewalToken(String token, long time, TimeUnit timeUnit) {
        return RedisHelper.expire(RedisKey.PREFIX_USER_TOKEN + token, time, timeUnit);
    }

    /**
     * 获取请求Token
     *
     * @param request 请求
     * @return token
     */
    public static String getToken(HttpServletRequest... request) {
        HttpServletRequest requestRes = SessionUtils.getRequest();
        if (request != null && request.length > 0) {
            requestRes = request[0];
        }
        String token = requestRes.getHeader(TokenCacheHolder.TOKEN_HEADER_KEY);
        if (token == null || token.length() < 8 || !"bearer".equalsIgnoreCase(token.substring(0, 6))) {
            return null;
        }
        return token.substring(7);
    }
}
