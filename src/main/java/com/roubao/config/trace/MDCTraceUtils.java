package com.roubao.config.trace;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 链路工具
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public class MDCTraceUtils {

    /**
     * 追踪id的名称
     */
    public static final String KEY_TRACE_ID = "traceId";

    /**
     * 日志链路追踪id信息头
     */
    public static final String TRACE_ID_HEADER = "x-traceId-header";


    /**
     * 创建traceId并赋值MDC
     */
    public static void generateAndPut() {
        String traceId = generateTraceId();
        MDC.put(KEY_TRACE_ID, traceId);
    }

    /**
     * 赋值MDC
     */
    public static void putTrace(String traceId) {
        MDC.put(KEY_TRACE_ID, traceId);
    }

    /**
     * 获取MDC中的traceId值
     */
    public static String getTraceId() {
        return MDC.get(KEY_TRACE_ID);
    }

    /**
     * 清除MDC的值
     */
    public static void removeTrace() {
        MDC.remove(KEY_TRACE_ID);
    }

    /**
     * 创建traceId
     */
    public static String generateTraceId() {
        return IdUtil.getSnowflake().nextIdStr();
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                return callable.call();
            } finally {
                // 最终清理MDC,避免内存溢出
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                // 最终清理MDC,避免内存溢出
                MDC.clear();
            }
        };
    }

    private MDCTraceUtils() {

    }
}
