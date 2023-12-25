package com.roubao.config.trace;

import org.slf4j.MDC;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * MDC链路追踪线程池
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
public class MDCTracePoolExecutor extends ThreadPoolExecutor {

    public MDCTracePoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(MDCTraceUtils.wrap(command, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(MDCTraceUtils.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(MDCTraceUtils.wrap(task, MDC.getCopyOfContextMap()), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(MDCTraceUtils.wrap(task, MDC.getCopyOfContextMap()));
    }
}
