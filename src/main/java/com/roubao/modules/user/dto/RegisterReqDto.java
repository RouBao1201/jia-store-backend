package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
@Schema(name = "用户注册请求实体", description = "用户注册请求实体")
@Data
@ToString
public class RegisterReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -9118776122854492640L;

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotBlank(message = "用户名不可为空")
    private String username;

    @Schema(name = "password", description = "密码", example = "admin")
    @NotBlank(message = "密码不可为空")
//    @Size(min = 8, message = "密码长度不能小于8位")
    private String password;

    @Schema(name = "type", description = "类型", example = "1")
    private Integer type;
}
