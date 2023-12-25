package com.roubao.config.trace;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 链路过滤器自动配置类
 *
 * @author: SongYanBin
 * @date: 2023-12-12
 */
@EnableConfigurationProperties(MDCTracePoolProperties.class)
public class MDCTraceConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MDCTraceConfiguration.class);

    @Bean("webTraceFilter")
    public WebTraceFilter webTraceFilter() {
        return new WebTraceFilter();
    }

    @Bean
    public FilterRegistrationBean<WebTraceFilter> filterRegistrationBean(WebTraceFilter webTraceFilter) {
        logger.info("TraceFilterConfiguration ==> Init WebTraceFilter.");
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

    @Bean("mdcTracePoolExecutor")
    public MDCTracePoolExecutor mdcTracePoolExecutor(MDCTracePoolProperties mdcTracePoolProperties) {
        logger.info("MDCTraceConfiguration ==> Init MDCTracePoolExecutor, properties: {}.", mdcTracePoolProperties.toString());
        return new MDCTracePoolExecutor(mdcTracePoolProperties.getCorePoolSize(),
                mdcTracePoolProperties.getMaximumPoolSize(),
                mdcTracePoolProperties.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(mdcTracePoolProperties.getBlockingQueueLength()),
                new ThreadFactoryBuilder()
                        .setNamePrefix(mdcTracePoolProperties.getPrefixName() + "-")
                        .setUncaughtExceptionHandler(
                                (thread, throwable) -> logger.error("MDCTracePoolExecutor => UncaughtExceptionHandler Thread[{}] execute exception. ErrorMessage:{}.", thread, throwable)).build(),
                mdcTracePoolProperties.getRejectedPolicy().getHandler()
        ) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if (r instanceof FutureTask<?>) {
                    try {
                        ((FutureTask<?>) r).get();
                    } catch (Exception e) {
                        logger.error("MDCTracePoolExecutor => AfterExecute. Runnable[{}] execute exception. ErrorMessage:{}.", r, e.getMessage());
                    }
                }
            }
        };
    }
}
