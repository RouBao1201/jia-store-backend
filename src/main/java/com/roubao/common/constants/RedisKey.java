package com.roubao.common.constants;

/**
 * Redis键常量
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
public interface RedisKey {
    /**
     * 用户短信验证码前缀
     */
    String PREFIX_USER_SMS_CODE = "user:sms:code:";
    /**
     * 用户登录token前缀
     */
    String PREFIX_USER_TOKEN = "user:token:";
}
