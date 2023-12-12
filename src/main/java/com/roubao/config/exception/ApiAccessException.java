package com.roubao.config.exception;

import java.io.Serial;

/**
 * 接口访问权限校验异常
 *
 * @author: SongYanBin
 * @date: 2023-12-12
 */
public class ApiAccessException extends BaseRuntimeException {
    @Serial
    private static final long serialVersionUID = -4715245646049230607L;

    @Override
    public int getCode() {
        return 40101;
    }

    @Override
    public String getMessage() {
        return "接口访问权限校验异常";
    }

    public ApiAccessException() {
    }

    public ApiAccessException(String message) {
        super(message);
    }

    public ApiAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiAccessException(Throwable cause) {
        super(cause);
    }
}
