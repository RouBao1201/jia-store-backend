package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册响应实体
 *
 * @author: SongYanBin
 * @date: 2023-12-13
 */
@Schema(name = "用户注册响应实体", description = "用户注册响应实体")
@Data
@ToString
public class RegisterRespDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1152270558002313211L;

    /**
     * success:注册成功
     * error:注册失败
     */
    @Schema(name = "status", description = "注册状态", example = "success")
    private String status;

    public static String transStatus(Integer registerCount) {
        if (registerCount > 0) {
            return "success";
        }
        return "error";
    }
}
