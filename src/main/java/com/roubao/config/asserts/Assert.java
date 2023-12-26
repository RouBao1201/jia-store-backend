package com.roubao.config.asserts;

import com.roubao.common.constants.RespCodeEnum;

/**
 * 断言工具枚举
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public enum Assert implements AssertPerformerExceptionHandler {
    PARAMETER_ERROR(RespCodeEnum.PARAMETER_ERROR.getCode(), "参数错误"),

    UNAUTHORIZED_ERROR(RespCodeEnum.UNAUTHORIZED_ERROR.getCode(), "未授权"),

    FORBIDDEN_ERROR(RespCodeEnum.FORBIDDEN_ERROR.getCode(), "禁止访问"),
    ;

    private final String code;

    private final String message;

    Assert(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
