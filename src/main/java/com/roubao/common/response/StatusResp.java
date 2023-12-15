package com.roubao.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 状态响应实体
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Schema(name = "状态响应实体", description = "状态响应实体")
@Data
@ToString
public class StatusResp implements Serializable {
    @Serial
    private static final long serialVersionUID = -7416246428022268408L;

    /**
     * success:成功
     * error:失败
     */
    @Schema(name = "status", description = "修改状态", example = "success")
    private String status;

    public static String transStatus(Integer registerCount) {
        if (registerCount > 0) {
            return "success";
        }
        return "error";
    }
}
