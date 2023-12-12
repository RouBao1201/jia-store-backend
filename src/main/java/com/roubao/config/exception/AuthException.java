package com.roubao.config.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 权限校验异常
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/9
 **/
public class AuthException extends BaseRuntimeException {
    @Serial
    private static final long serialVersionUID = -2801956111099369186L;

    @Override
    public int getCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
}
