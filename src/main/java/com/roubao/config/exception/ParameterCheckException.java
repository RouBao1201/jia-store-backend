package com.roubao.config.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 参数校验异常
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
public class ParameterCheckException extends BaseRuntimeException {
    @Serial
    private static final long serialVersionUID = 8276742042262341394L;

    @Override
    public int getCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    public ParameterCheckException() {
    }

    public ParameterCheckException(String message) {
        super(message);
    }

    public ParameterCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterCheckException(Throwable cause) {
        super(cause);
    }
}
