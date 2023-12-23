package com.roubao.config.asserts;

/**
 * 断言逻辑执行器
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public interface AssertLogicPerformer extends AssertExceptionBuilder {
    /**
     * 不可为空
     *
     * @param obj 校验数据
     */
    default void notNull(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    /**
     * 不可为空
     *
     * @param obj          校验数据
     * @param errorMessage 异常信息
     */
    default void notNull(Object obj, String errorMessage) {
        if (obj == null) {
            throw newException(errorMessage);
        }
    }

    /**
     * 满足条件则抛出异常
     *
     * @param condition 判断条件
     */
    default void throwIf(boolean condition) {
        if (condition) {
            throw newException();
        }
    }

    /**
     * 满足条件则抛出异常
     *
     * @param condition    判断条件
     * @param errorMessage 异常信息
     */
    default void throwIf(boolean condition, String errorMessage) {
        if (condition) {
            throw newException(errorMessage);
        }
    }

    /**
     * 直接抛出异常
     *
     * @param errorMessage 异常信息
     */
    default void directThrow(String errorMessage) {
        throw newException(errorMessage);
    }

    /**
     * 直接抛出异常
     */
    default void directThrow() {
        throw newException();
    }
}
