package com.roubao.config.cache.sms;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短信验证码缓存持有类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/16
 **/
@Data
@ToString
@ConfigurationProperties(prefix = "cache.sms")
public class SmsCodeProperties {
    /**
     * 过期时间（单位秒）
     */
    private Long expireAfterWrite = 60L;
    /**
     * 初始化容量
     */
    private Integer initialCapacity = 100;
    /**
     * 最大容量
     */
    private Long maximumSize = 200L;
}
