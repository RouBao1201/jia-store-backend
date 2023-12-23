package com.roubao.config.asserts;

/**
 * 异常基础方法接口
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public interface ExceptionBasicFunction {
    /**
     * 获取异常码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMessage();
}
