package com.roubao.config.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

/**
 * 请求信息拦截器配置
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/4/4
 **/
@Data
@ConfigurationProperties("interceptor.auth")
public class AuthInterceptorProperties {
    /**
     * 需要拦截的路径
     */
    private String[] interceptPatterns = {"/**"};

    /**
     * 无需拦截的路径
     */
    private String[] excludePatterns = {};


    @Override
    public String toString() {
        return "AuthInterceptorProperties{" +
                "interceptPatterns=" + Arrays.toString(interceptPatterns) +
                ", excludePatterns=" + Arrays.toString(excludePatterns) +
                '}';
    }
}
