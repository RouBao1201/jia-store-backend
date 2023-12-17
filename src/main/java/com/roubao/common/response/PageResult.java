package com.roubao.common.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;

/**
 * 分页响应结果
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/17
 **/
@Data
@ToString
public class PageResult<T> extends RespResult<PageList<T>> {
    @Serial
    private static final long serialVersionUID = 3229899843667734959L;

    /**
     * 分页查询结果成功响应
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     泛型
     * @return 分页结果数据
     */
    public static <T> PageResult<T> success(String message, PageList<T> data) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setData(data);
        pageResult.setCode(RespResult.CODE_SUCCESS);
        pageResult.setMsg(message);
        return pageResult;
    }

    /**
     * 分页查询结果成功响应
     *
     * @param data 数据
     * @param <T>  泛型
     * @return 分页结果数据
     */
    public static <T> PageResult<T> success(PageList<T> data) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setData(data);
        pageResult.setCode(RespResult.CODE_SUCCESS);
        pageResult.setMsg(RespResult.MESSAGE_SUCCESS);
        return pageResult;
    }
}
