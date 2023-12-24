package com.roubao.config.trace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * 链路过滤器自动配置类
 *
 * @author: SongYanBin
 * @date: 2023-12-12
 */
@Slf4j
public class TraceFilterConfiguration {

    @Bean("webTraceFilter")
    public WebTraceFilter webTraceFilter() {
        return new WebTraceFilter();
    }

    @Bean
    public FilterRegistrationBean<WebTraceFilter> filterRegistrationBean(WebTraceFilter webTraceFilter) {
        log.info("TraceFilterConfiguration ==> Init WebTraceFilter.");
        //1.创建FilterRegistrationBean这个对象, 一个过滤器注册器,注册一个过滤器
        FilterRegistrationBean<WebTraceFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        //注册一个过滤器
        filterRegistrationBean.setFilter(webTraceFilter);
        //过滤器的配置, 设置拦截的url
        filterRegistrationBean.addUrlPatterns("/*");
        //给过滤器起名字
        filterRegistrationBean.setName("WebTraceFilterRegistrationBean");
        //设置过滤器的执行顺序
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 3);
        return filterRegistrationBean;
    }
}
