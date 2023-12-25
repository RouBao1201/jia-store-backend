package com.roubao.config.exception;

import com.roubao.common.constants.RespCodeEnum;

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
    public String getCode() {
        return RespCodeEnum.FORBIDDEN_ERROR.getCode();
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
