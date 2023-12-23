package com.roubao.config.asserts;

/**
 * 断言工具枚举
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public enum Assert implements AssertPerformerExceptionHandler {
    PARAMETER_ERROR(400, "参数错误"),

    AUTH_ERROR(403, "权限异常"),
    ;

    private final Integer code;

    private final String message;

    Assert(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
