package com.roubao.config.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池拒绝策略
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/1
 **/
@Slf4j
public class ThreadPoolRejectedHandler {

    /**
     * 由调用线程（提交任务的线程）处理该任务
     */
    public static CallerRunsPolicyHandler callerRunsPolicy() {
        return new CallerRunsPolicyHandler();
    }

    /**
     * 丢弃任务并抛出RejectedExecutionException异常
     */
    public static AbortPolicyHandler abortPolicy() {
        return new AbortPolicyHandler();
    }

    /**
     * 丢弃任务,但是不抛出异常
     */
    public static DiscardPolicyHandler discardPolicy() {
        return new DiscardPolicyHandler();
    }

    /**
     * 丢弃队列最前面的任务,然后重新提交被拒绝的任务
     */
    public static DiscardOldestPolicyHandler discardOldestPolicy() {
        return new DiscardOldestPolicyHandler();
    }

    /**
     * 饱和策略一：调用者线程执行策略
     * 在调用者中执行被拒绝任务的run方法。除非线程池showdown,否则直接丢弃线程
     */
    public static class CallerRunsPolicyHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            recordLog(executor);

            // 判断线程池是否在正常运行,如果线程池在正常运行则由调用者线程执行被拒绝的任务。如果线程池停止运行,则直接丢弃该任务
            if (!executor.isShutdown()) {
                r.run();
            }
        }
    }


    /**
     * 饱和策略二：终止策略
     * 丢弃被拒绝的任务,并抛出拒绝执行异常
     */
    public static class AbortPolicyHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            recordLog(executor);
            throw new RejectedExecutionException("Task: " + r.toString() + ". Execute AbortPolicy. ThreadPool: " + executor);
        }
    }


    /**
     * 饱和策略三：丢弃策略
     * 什么都不做直接丢弃被拒绝的任务
     */
    public static class DiscardPolicyHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            recordLog(executor);
        }
    }

    /**
     * 饱和策略四：弃老策略
     * 丢弃最早放入阻塞队列中的线程,并尝试将拒绝任务加入阻塞队列
     */
    public static class DiscardOldestPolicyHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            recordLog(executor);
            // 判断线程池是否正常运行,如果线程池正常运行则弹出（或丢弃）最早放入阻塞队列中的任务,并尝试将拒绝任务加入阻塞队列。如果线程池停止运行,则直接丢弃该任务
            if (!executor.isShutdown()) {
                executor.getQueue().poll();
                executor.execute(r);
            }
        }
    }

    private static void recordLog(ThreadPoolExecutor executor) {
        log.warn("ThreadPoolRejectedHandler => The current thread pool exploded. Run thread count:" + executor.getPoolSize() + "; Active thread count:" + executor.getActiveCount() + "; Blocking queue count:" + executor.getQueue().size() + ".");
    }


    private ThreadPoolRejectedHandler() {

    }
}
