package com.roubao.util;

import com.roubao.config.exception.BaseRuntimeException;

/**
 * 判断执行器工具类
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
public class EitherUtils {
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
        boolT(condition).doIf(() -> {
            throw new BaseRuntimeException(errorCode, errorMsg);
        });
    }

    /**
     * bool判断条件决定走哪个分支代码
     *
     * @param condition 条件
     * @return true和false分支逻辑函数
     */
    public static EitherHandleFunction boolE(boolean condition) {
        return (trueHandle, falseHandle) -> {
            if (condition) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * 条件为true时执行
     *
     * @param condition 条件
     * @return 执行函数
     */
    public static DoHandleFunction boolT(boolean condition) {
        return (trueHandle) -> {
            if (condition) {
                trueHandle.run();
            }
        };
    }

    /**
     * 条件为false时执行
     *
     * @param condition 条件
     * @return 执行函数
     */
    public static DoHandleFunction boolF(boolean condition) {
        return boolT(!condition);
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

    @FunctionalInterface
    public interface DoHandleFunction {
        /**
         * 分支处理逻辑
         *
         * @param trueHandle true分支处理逻辑
         */
        void doIf(Runnable trueHandle);
    }

    private EitherUtils() {

    }
}
