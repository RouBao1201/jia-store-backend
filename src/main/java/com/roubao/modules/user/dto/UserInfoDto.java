package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: SongYanBin
 * @date: 2023-12-11
 */
@Data
@ToString
public class UserInfoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6447250190138901371L;

    @Schema(name = "id", description = "用户id")
    private Integer id;

    @Schema(name = "username", description = "用户名")
    private String username;

    @Schema(name = "username", description = "用户状态")
    private Integer status;

    @Schema(name = "superAdmin", description = "用户是否为超级管理员")
    private boolean superAdmin;
}
