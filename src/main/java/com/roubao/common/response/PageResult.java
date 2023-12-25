package com.roubao.common.response;

import com.roubao.common.constants.RespCodeEnum;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 分页响应结果
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/17
 **/
@ToString
public class PageResult<T> extends RespResult<PageList<T>> {
    @Serial
    private static final long serialVersionUID = 3229899843667734959L;

    public PageResult(HttpStatus status, RespBody<PageList<T>> body) {
        super(status, body);
    }

    /**
     * 分页查询结果成功响应
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     泛型
     * @return 分页结果数据
     */
    public static <T> PageResult<T> success(String message, PageList<T> data) {
        return new PageResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.SUCCESS.getCode(), message, data));
    }

    /**
     * 分页查询结果成功响应
     *
     * @param data 数据
     * @param <T>  泛型
     * @return 分页结果数据
     */
    public static <T> PageResult<T> success(PageList<T> data) {
        return new PageResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.SUCCESS.getCode(),
                RespCodeEnum.SUCCESS.getMessage(), data));
    }
}
