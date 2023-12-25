package com.roubao.config.trace;

import com.roubao.config.thread.RejectedPolicy;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Data
@ToString
@ConfigurationProperties(prefix = "thread-pool.mdc-trace")
public class MDCTracePoolProperties {
    /**
     * 线程池前缀
     */
    private String prefixName = "mdc-trace-pool";

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
