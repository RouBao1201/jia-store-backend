package com.roubao.config.thread;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
@Data
@ToString
@ConfigurationProperties(prefix = "thread-pool.common")
public class ThreadPoolProperties {
    /**
     * 线程池前缀
     */
    private String prefixName = "common-pool";

    /**
     * 等待队列长度
     */
    private int blockingQueueLength = 1000;

    /**
     * 闲置线程存活时间
     */
    private long keepAliveTime = 60000;

    /**
     * 核心线程数
     */
    private int corePoolSize = 10;

    /**
     * 最大线程数
     */
    private int maximumPoolSize = 20;

    /**
     * 拒绝策略
     */
    private RejectedPolicy rejectedPolicy = RejectedPolicy.ABORT;
}
