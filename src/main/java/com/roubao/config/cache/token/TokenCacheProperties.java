package com.roubao.config.cache.token;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Token缓存配置
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/11
 **/
@Data
@ToString
@ConfigurationProperties(prefix = "cache.token")
public class TokenCacheProperties {
    /**
     * 过期时间（单位秒）
     */
    private Long expireAfterAccess = 600L;
    /**
     * 初始化容量
     */
    private Integer initialCapacity = 100;
    /**
     * 最大容量
     */
    private Long maximumSize = 200L;
}
