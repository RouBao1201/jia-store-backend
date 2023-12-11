package com.roubao.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 结果响应体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
@Schema(name = "结果响应体", description = "结果响应体")
@Data
@ToString
public class IResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1460387755383309189L;

    @Schema(name = "success", description = "成功标志位")
    private boolean success;

    @Schema(name = "message", description = "结果信息")
    private String message;


    public static IResult ok() {
        return new IResult(true, "ok");
    }

    public static IResult no() {
        return new IResult(false, "no");
    }

    private IResult() {
    }

    private IResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
