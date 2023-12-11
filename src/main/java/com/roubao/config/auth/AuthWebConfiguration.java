package com.roubao.config.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 请求信息拦截器配置
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/4/4
 **/
@Configuration
@EnableConfigurationProperties(AuthInterceptorProperties.class)
public class AuthWebConfiguration implements WebMvcConfigurer {

    private final AuthInterceptorProperties authInterceptorProperties;

    public AuthWebConfiguration(AuthInterceptorProperties authInterceptorProperties) {
        this.authInterceptorProperties = authInterceptorProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPattern 添加拦截规则 /** 拦截所有包括静态资源
        // excludePathPattern 排除拦截规则 所以我们需要放开静态资源的拦截
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns(authInterceptorProperties.getInterceptPatterns())
                .excludePathPatterns(authInterceptorProperties.getExcludePatterns());
    }
}
