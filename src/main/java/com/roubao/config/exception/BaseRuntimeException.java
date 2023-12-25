package com.roubao.config.exception;

import com.roubao.common.constants.RespCodeEnum;

import java.io.Serial;

/**
 * 基础异常类
 *
 * @author: SongYanBin
 * @date: 2023-12-12
 */
public class BaseRuntimeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4569556263785675784L;

    private String code = RespCodeEnum.INTERNAL_SERVER_ERROR.getCode();

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        if (super.getMessage() == null) {
            return "服务器异常，请稍后重试";
        }
        return super.getMessage();
    }

    public BaseRuntimeException() {
    }

    public BaseRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

}
