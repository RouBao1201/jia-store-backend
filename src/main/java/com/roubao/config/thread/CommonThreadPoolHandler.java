package com.roubao.config.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池持有工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class CommonThreadPoolHandler {

    private static ThreadPoolExecutor threadPoolExecutor = null;

    private final ThreadPoolProperties threadPoolProperties;

    public CommonThreadPoolHandler(ThreadPoolProperties threadPoolProperties) {
        this.threadPoolProperties = threadPoolProperties;
    }

    /**
     * 执行线程逻辑
     *
     * @param runnable 线程逻辑
     */
    public void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }

    /**
     * 执行线程逻辑
     *
     * @param callable callable
     * @param <T>      返回值类型
     * @return 返回值
     */
    public <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }

    /**
     * 获取活跃的线程数
     *
     * @return 活跃线程数量
     */
    public int getActiveCount() {
        return getThreadPool().getActiveCount();
    }

    /**
     * 调用 shutdown() 方法之后线程池并不是立刻就被关闭,因为这时线程池中可能还有很多任务正在被执行,或是任务队列中有大量正在等待被执行的任务。
     * 调用 shutdown() 方法后线程池会在执行完正在执行的任务和队列中等待的任务后才彻底关闭。
     * 调用 shutdown() 方法后如果还有新的任务被提交，线程池则会根据拒绝策略直接拒绝后续新提交的任务
     */
    public void shutdown() {
        getThreadPool().shutdown();
    }

    /**
     * 在执行 shutdownNow 方法之后,首先会给所有线程池中的线程发送 interrupt 中断信号。
     * 尝试中断这些任务的执行,然后会将任务队列中正在等待的所有任务转移到一个 List 中并返回,我们可以根据返回的任务 List 来进行一些补救的操作
     *
     * @return 未执行的任务（可用于补偿）
     */
    public List<Runnable> shutdownNow() {
        return getThreadPool().shutdownNow();
    }

    /**
     * 判断线程池是否已经开始了关闭工作。
     * 是否执行了 shutdown 或者 shutdownNow 方法。
     * 并不代表线程池此时已经彻底关闭了,这仅仅代表线程池开始了关闭的流程;也就是说,此时可能线程池中依然有线程在执行任务,队列里也可能有等待被执行的任务。
     */
    public boolean isShutdown() {
        return getThreadPool().isShutdown();
    }

    /**
     * 这个方法可以检测线程池是否真正“终结”了,这不仅代表线程池已关闭,同时代表线程池中的所有任务都已经都执行完毕了。
     * 直到所有任务都执行完毕了,调用 isTerminated() 方法才会返回 true,这表示线程池已关闭并且线程池内部是空的,所有剩余的任务都执行完毕了。
     *
     * @return 线程池是否征程终结
     */
    public boolean isTerminated() {
        return getThreadPool().isTerminated();
    }

    /**
     * 获取线程池（加锁避免多线调用时,一直创建线程池消耗资源）
     *
     * @return ThreadPoolExecutor
     */
    public synchronized ThreadPoolExecutor getThreadPool() {
        if (threadPoolExecutor == null) {
            /*
             * 【submit】 提交和 【execute】 线程任务捕获异常方式不同
             * execute: 线程工厂设置UncaughtExceptionHandler处理异常
             * submit: 重写线程池afterExecute方法处理异常
             */
            threadPoolExecutor = new ThreadPoolExecutor(
                    this.threadPoolProperties.getCorePoolSize(),
                    this.threadPoolProperties.getMaximumPoolSize(),
                    this.threadPoolProperties.getKeepAliveTime(),
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(this.threadPoolProperties.getBlockingQueueLength()),
                    new ThreadFactoryBuilder()
                            .setNamePrefix(this.threadPoolProperties.getPrefixName() + "-")
                            .setUncaughtExceptionHandler(
                                    (thread, throwable) -> log.error("CommonThreadPoolHandler => UncaughtExceptionHandler Thread[{}] execute exception. ErrorMessage:{}.", thread, throwable)).build(),
                    this.threadPoolProperties.getRejectedPolicy().getHandler()) {
                @Override
                protected void afterExecute(Runnable r, Throwable t) {
                    if (r instanceof FutureTask<?>) {
                        try {
                            ((FutureTask<?>) r).get();
                        } catch (Exception e) {
                            log.error("CommonThreadPoolHandler => AfterExecute. Runnable[{}] execute exception. ErrorMessage:{}.", r, e.getMessage());
                        }
                    }
                }
            };
            log.info("CommonThreadPoolHandler => Init threadPoolExecutor {prefixName:" + this.threadPoolProperties.getPrefixName() + ", coreSize:" + this.threadPoolProperties.getCorePoolSize() + ", maxSize:" + this.threadPoolProperties.getMaximumPoolSize() + ", keepAliveTime:" + this.threadPoolProperties.getKeepAliveTime() + "ms, blockQueueLength:" + this.threadPoolProperties.getBlockingQueueLength() + "}.");
        }
        return threadPoolExecutor;
    }
}
