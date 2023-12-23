package com.roubao.config.trace;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;

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

    private MDCTraceUtils() {

    }
}
