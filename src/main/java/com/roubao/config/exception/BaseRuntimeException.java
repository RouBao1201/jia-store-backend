package com.roubao.config.exception;

import org.springframework.http.HttpStatus;

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

    private final int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public int getCode() {
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
