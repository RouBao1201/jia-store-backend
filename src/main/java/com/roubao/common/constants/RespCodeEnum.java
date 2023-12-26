package com.roubao.common.constants;

import lombok.Getter;

/**
 * 全局响应编码枚举
 * A:客户端问题
 * B:服务端问题
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Getter
public enum RespCodeEnum {
    SUCCESS("0000", "success"),
    PARAMETER_ERROR("A0001", "参数错误"),
    UNAUTHORIZED_ERROR("A0401", "未授权"),
    FORBIDDEN_ERROR("A0403", "禁止访问"),
    INTERNAL_SERVER_ERROR("B0500", "服务器异常");

    private final String code;

    private final String message;

    RespCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
