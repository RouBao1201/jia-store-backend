package com.roubao.config.exception;

import java.io.Serial;

/**
 * 参数校验异常
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
public class ParameterCheckException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8276742042262341394L;

    public ParameterCheckException() {
    }

    public ParameterCheckException(String message) {
        super(message);
    }

    public ParameterCheckException(Throwable cause) {
        super(cause);
    }

    public ParameterCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
