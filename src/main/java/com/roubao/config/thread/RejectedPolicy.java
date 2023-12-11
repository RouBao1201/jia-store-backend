package com.roubao.config.thread;

import lombok.Getter;

import java.util.concurrent.RejectedExecutionHandler;

/**
 * 线程池拒绝策略
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/4/5
 **/
@Getter
public enum RejectedPolicy {
    /**
     * 饱和策略一：调用者线程执行策略
     * 在调用者中执行被拒绝任务的run方法。除非线程池showdown,否则直接丢弃线程
     */
    CALLER_RUNS(new ThreadPoolRejectedHandler.CallerRunsPolicyHandler()),

    /**
     * 饱和策略二：终止策略
     * 丢弃被拒绝的任务,并抛出拒绝执行异常
     */
    ABORT(new ThreadPoolRejectedHandler.AbortPolicyHandler()),

    /**
     * 饱和策略三：丢弃策略
     * 什么都不做直接丢弃被拒绝的任务
     */
    DISCARD(new ThreadPoolRejectedHandler.DiscardPolicyHandler()),

    /**
     * 饱和策略四：弃老策略
     * 丢弃最早放入阻塞队列中的线程,并尝试将拒绝任务加入阻塞队列
     */
    DISCARD_OLDEST(new ThreadPoolRejectedHandler.DiscardOldestPolicyHandler());

    private final RejectedExecutionHandler handler;

    RejectedPolicy(RejectedExecutionHandler handler) {
        this.handler = handler;
    }
}
