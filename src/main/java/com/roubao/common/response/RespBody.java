package com.roubao.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应数据体
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Schema(name = "统一响应数据体", description = "统一响应数据体")
@Data
@ToString
public class RespBody<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3498002769290636922L;
    /**
     * 响应编码
     */
    @Schema(name = "code", description = "响应编码")
    private String code;

    /**
     * 响应信息
     */
    @Schema(name = "message", description = "响应信息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(name = "data", description = "响应数据")
    private T data;

    /**
     * 链路ID
     */
    @Schema(name = "traceId", description = "链路ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceId;

    public RespBody(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
