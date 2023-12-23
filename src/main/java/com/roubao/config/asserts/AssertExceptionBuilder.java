package com.roubao.config.asserts;

/**
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public interface AssertExceptionBuilder {
    /**
     * 新增异常
     *
     * @return 断言异常AssertException
     */
    AssertException newException();

    /**
     * 新增异常
     *
     * @param errorMessage 异常信息
     * @return 断言异常AssertException
     */
    AssertException newException(String errorMessage);
}
