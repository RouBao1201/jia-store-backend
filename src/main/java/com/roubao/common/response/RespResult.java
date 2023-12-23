package com.roubao.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.roubao.config.trace.MDCTraceUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础响应体
 *
 * @author SongYanBin
 * @copyright ©2023-2099 SongYanBin. All rights reserved.
 * @since 2023/3/4
 **/

@Schema(name = "统一响应实体", description = "统一响应实体")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RespResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 6621781246351039577L;

    /**
     * 成功响应返回码
     */
    public static final Integer CODE_SUCCESS = 200;
    /**
     * 成功响应消息
     */
    public static final String MESSAGE_SUCCESS = "success";
    /**
     * 错误响应返回码
     */
    public static final Integer CODE_ERROR = 500;
    /**
     * 错误响应消息
     */
    public static final String MESSAGE_ERROR = "error";

    /**
     * 响应编码
     */
    @Schema(name = "code", description = "响应编码")
    private Integer code;

    /**
     * 响应信息
     */
    @Schema(name = "message", description = "响应信息")
    private String msg;

    /**
     * 响应数据
     */
    @Schema(name = "data", description = "响应数据")
    private T data;

    /**
     * 链路ID
     */
    @Schema(name = "traceId", description = "链路ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceId = MDCTraceUtils.getTraceId();

    public RespResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功响应
     *
     * @param <T> 数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> success() {
        return new RespResult<>(CODE_SUCCESS, MESSAGE_SUCCESS, null);
    }

    /**
     * 成功响应
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return RespResult
     */
    public static <T> RespResult<T> success(String message) {
        return new RespResult<>(CODE_SUCCESS, message, null);
    }

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T>  数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> success(T data) {
        return new RespResult<>(CODE_SUCCESS, MESSAGE_SUCCESS, data);
    }

    /**
     * 成功响应
     *
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> success(String message, T data) {
        return new RespResult<>(CODE_SUCCESS, message, data);
    }

    /**
     * 成功响应
     *
     * @param code    响应编码
     * @param message 响应信息
     * @param <T>     数据枚举
     * @return RespResult
     */
    public static <T> RespResult<T> success(Integer code, String message) {
        return new RespResult<>(code, message, null);
    }

    /**
     * 成功响应
     *
     * @param code    响应编码
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     数据枚举
     * @return RespResult
     */
    public static <T> RespResult<T> success(Integer code, String message, T data) {
        return new RespResult<>(code, message, data);
    }

    /**
     * 异常响应
     *
     * @param <T> 数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> error() {
        return new RespResult<>(CODE_ERROR, MESSAGE_ERROR, null);
    }

    /**
     * 异常响应
     *
     * @param data 响应数据
     * @param <T>  数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> error(T data) {
        return new RespResult<>(CODE_ERROR, MESSAGE_ERROR, data);
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> error(String message) {
        return new RespResult<>(CODE_ERROR, message, null);
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> error(String message, T data) {
        return new RespResult<>(CODE_ERROR, message, data);
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param code    响应编码
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> error(Integer code, String message) {
        return new RespResult<>(code, message, null);
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param code    响应编码
     * @param data    响应数据
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> error(Integer code, String message, T data) {
        return new RespResult<>(code, message, data);
    }
}
