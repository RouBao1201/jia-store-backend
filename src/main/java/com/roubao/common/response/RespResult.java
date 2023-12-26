package com.roubao.common.response;

import com.roubao.common.constants.RespCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.lang.ref.SoftReference;

/**
 * 基础响应体
 *
 * @author SongYanBin
 * @copyright ©2023-2099 SongYanBin. All rights reserved.
 * @since 2023/3/4
 **/

@Schema(name = "统一响应实体", description = "统一响应实体")
@ToString
public class RespResult<T> extends ResponseEntity<RespBody<T>> implements Serializable {

    @Serial
    private static final long serialVersionUID = 6621781246351039577L;

    private static SoftReference<RespResult<Object>> EMPTY_SUCCESS = null;

    private static SoftReference<RespResult<Object>> EMPTY_FAILURE = null;

    public RespResult(HttpStatusCode status, RespBody<T> body) {
        super(body, status);
    }

    /**
     * 成功响应
     *
     * @return RespResult
     */
    public static RespResult<Object> success() {
        return buildEmptySuccess();
    }

    /**
     * 成功响应
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return RespResult
     */
    public static <T> RespResult<T> success(String message) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.SUCCESS.getCode(), message, null));
    }

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T>  数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> success(T data) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage(), data));
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
        return new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.SUCCESS.getCode(), message, data));
    }

    /**
     * 成功响应
     *
     * @param code    响应编码
     * @param message 响应信息
     * @param <T>     数据枚举
     * @return RespResult
     */
    public static <T> RespResult<T> success(String code, String message) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(code, message, null));
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
    public static <T> RespResult<T> success(String code, String message, T data) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(code, message, data));
    }

    /**
     * 异常响应
     *
     * @param <T> 数据泛型
     * @return RespResult
     */
    public static RespResult<Object> failure() {
        return buildEmptyFailure();
    }

    /**
     * 异常响应
     *
     * @param data 响应数据
     * @param <T>  数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> failure(T data) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.PARAMETER_ERROR.getCode(), RespCodeEnum.PARAMETER_ERROR.getMessage(), data));
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> failure(String message) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.PARAMETER_ERROR.getCode(), message, null));
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> failure(String message, T data) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.PARAMETER_ERROR.getCode(), message, data));
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param code    响应编码
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> failure(String code, String message) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(code, message, null));
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
    public static <T> RespResult<T> failure(String code, String message, T data) {
        return new RespResult<>(HttpStatus.OK, new RespBody<>(code, message, data));
    }

    /**
     * 失败响应
     *
     * @param status  http状态码
     * @param code    响应编码
     * @param message 响应信息
     * @param <T>     数据泛型
     * @return RespResult
     */
    public static <T> RespResult<T> failure(HttpStatus status, String code, String message) {
        return new RespResult<>(status, new RespBody<>(code, message, null));
    }

    /**
     * 构造成功空响应
     *
     * @return 统一空数据响应
     */
    private static RespResult<Object> buildEmptySuccess() {
        RespResult<Object> result = EMPTY_SUCCESS == null ? null : EMPTY_SUCCESS.get();
        if (result == null) {
            result = new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage(), null));
            EMPTY_SUCCESS = new SoftReference<>(result);
        }
        return result;
    }

    /**
     * 构造失败空响应
     *
     * @return 同意失败空数据响应
     */
    private static RespResult<Object> buildEmptyFailure() {
        RespResult<Object> result = EMPTY_FAILURE == null ? null : EMPTY_FAILURE.get();
        if (result == null) {
            result = new RespResult<>(HttpStatus.OK, new RespBody<>(RespCodeEnum.PARAMETER_ERROR.getCode(), RespCodeEnum.PARAMETER_ERROR.getMessage(), null));
            EMPTY_FAILURE = new SoftReference<>(result);
        }
        return result;
    }
}
