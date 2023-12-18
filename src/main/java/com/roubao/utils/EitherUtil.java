package com.roubao.utils;

import com.roubao.config.exception.BaseRuntimeException;

/**
 * 判断执行器工具类
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
public class EitherUtil {
    /**
     * 对象为空则抛出异常
     *
     * @param obj       对象
     * @param errorCode 错误编码
     * @param errorMsg  错误信息
     */
    public static void throwIfNull(Object obj, int errorCode, String errorMsg) {
        throwIf(obj == null, errorCode, errorMsg);
    }

    /**
     * 对象不为空则抛异常
     *
     * @param obj       对象
     * @param errorCode 错误编码
     * @param errorMsg  错误信息
     */
    public static void throwIfNotNull(Object obj, int errorCode, String errorMsg) {
        throwIf(obj != null, errorCode, errorMsg);
    }

    /**
     * 判断是否抛出异常
     *
     * @param condition 条件
     * @param errorCode 错误编码
     * @param errorMsg  错误信息
     */
    public static void throwIf(boolean condition, int errorCode, String errorMsg) {
        bool(condition).either(() -> {
            // 满足条件抛出异常
            throw new BaseRuntimeException(errorCode, errorMsg);
        }, () -> {
            // 不满足条件不做操作
        });
    }

    /**
     * bool判断条件决定走哪个分支代码
     *
     * @param condition 条件
     * @return true和false分支逻辑函数
     */
    public static EitherHandleFunction bool(boolean condition) {
        return (trueHandle, falseHandle) -> {
            if (condition) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    @FunctionalInterface
    public interface EitherHandleFunction {
        /**
         * 分支处理逻辑
         *
         * @param trueHandle  true分支处理逻辑
         * @param falseHandle false分支处理逻辑
         */
        void either(Runnable trueHandle, Runnable falseHandle);
    }

    private EitherUtil() {

    }
}
