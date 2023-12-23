package com.roubao.config.asserts;

import lombok.Getter;

import java.io.Serial;

/**
 * 断言异常
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Getter
public class AssertException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5645312404551073039L;

    private final Integer code;

    public AssertException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
