package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 账户密码修改请求实体
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Schema(name = "账户密码修改请求实体", description = "账户密码修改请求实体")
@Data
@ToString
public class ReviseReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1935922855720594869L;

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotBlank(message = "用户名不可为空")
    private String username;

    @Schema(name = "newPassword", description = "新密码", example = "admin")
    @NotBlank(message = "新密码不可为空")
    private String newPassword;

    @Schema(name = "checkPassword", description = "确认密码", example = "admin")
    @NotBlank(message = "确认密码不可为空")
    private String checkPassword;

    @Schema(name = "smsCode", description = "短信验证码", example = "123456")
    @NotBlank(message = "短信验证码不可为空")
    private String smsCode;
}
