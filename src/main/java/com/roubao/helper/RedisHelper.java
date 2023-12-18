package com.roubao.helper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
@Component
public class RedisHelper implements InitializingBean {
    private static RedisTemplate<String, Object> redisTemplateStatic;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisHelper(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisTemplateStatic = this.redisTemplate;
    }

    /**
     * 设置键值
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        redisTemplateStatic.opsForValue().set(key, value);
    }

    /**
     * 设置键值（指定过期时间）
     *
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    public static void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplateStatic.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 若key存在,则重新设置value值
     *
     * @param key   键
     * @param value 值
     */
    public static boolean setIfAbsent(String key, String value) {
        return Boolean.TRUE.equals(redisTemplateStatic.opsForValue().setIfAbsent(key, value));
    }

    /**
     * 设置key的过期时间
     *
     * @param key     键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 是否成功
     */
    public static boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplateStatic.expire(key, timeout, unit));
    }

    /**
     * 设置key具体在哪个时间过期
     *
     * @param key  键
     * @param date 具体的过期时间
     */
    public static void expireAt(String key, Date date) {
        redisTemplateStatic.expireAt(key, date);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 数据
     */
    public static Object get(String key) {
        return redisTemplateStatic.opsForValue().get(key);
    }

    /**
     * 获取数据,指定返回类型
     *
     * @param key   键
     * @param clazz 返回值类型
     * @param <T>   类型泛型
     * @return 返回值
     */
    public static <T> T get(String key, Class<T> clazz) {
        ValueOperations<String, Object> value = redisTemplateStatic.opsForValue();
        return (T) value.get(key);
    }

    /**
     * 获取多个Key
     *
     * @param pattern 表达式
     * @return Key集合
     */
    public static Set<String> keys(String pattern) {
        return redisTemplateStatic.keys(pattern);
    }

    /**
     * 异步删除缓存
     *
     * @param keys 多个keys
     */
    public static void unlink(String... keys) {
        if (keys.length == 1) {
            redisTemplateStatic.unlink(keys[0]);
        } else {
            redisTemplateStatic.unlink(Arrays.asList(keys));
        }
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return 是否成功
     */
    public static boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplateStatic.delete(key));
    }

    /**
     * 判断是否存在key
     *
     * @param key 键
     * @return 是否存在
     */
    public static boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplateStatic.hasKey(key));
    }

    /**
     * 修改key的名称
     *
     * @param oldKey 旧键名
     * @param newKey 新键名
     */
    public static void rename(String oldKey, String newKey) {
        redisTemplateStatic.rename(oldKey, newKey);
    }

    /**
     * 如果存在key,则修改key的名称
     *
     * @param oldKey 旧键名
     * @param newKey 新键名
     */
    public static void renameIfAbsent(String oldKey, String newKey) {
        redisTemplateStatic.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 根据key获取value类型
     *
     * @param key 键
     * @return 数据类型
     */
    public static DataType type(String key) {
        return redisTemplateStatic.type(key);
    }

    /**
     * 获取某个key的剩余过期时间
     *
     * @param key 键
     * @return 剩余过期时间（秒）
     */
    public static long getExpire(String key) {
        return Long.parseLong(String.valueOf(redisTemplateStatic.getExpire(key)));
    }

    /**
     * 获取某个key的剩余过期时间,指定单位
     *
     * @param key  键
     * @param unit 时间单位
     * @return 剩余过期时间
     */
    public static long getExpire(String key, TimeUnit unit) {
        return Long.parseLong(String.valueOf(redisTemplateStatic.getExpire(key, unit)));
    }

    /**
     * 在key原本的value后追加数据
     *
     * @param key   键
     * @param value 追加的数据
     */
    public static Integer append(String key, String value) {
        return redisTemplateStatic.opsForValue().append(key, value);
    }

    /**
     * 增加或减小值
     *
     * @param key   键
     * @param delta 增加或减小数量
     * @return
     */
    public static long increment(String key, long delta) {
        return redisTemplateStatic.opsForValue().increment(key, delta);
    }
}
