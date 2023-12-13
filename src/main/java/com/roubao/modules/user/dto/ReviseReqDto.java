package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改密码请求实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
@Schema(name = "用户修改密码请求实体", description = "用户修改密码请求实体")
@Data
@ToString
public class ReviseReqDto implements Serializable {

    public static final String TYPE_SMS = "sms";
    public static final String TYPE_OLD_PASSWORD = "old_password";

    @Serial
    private static final long serialVersionUID = 7573677603499973999L;

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotBlank(message = "用户名不可为空")
    private String username;

    @Schema(name = "password", description = "密码", example = "admin")
    @NotBlank(message = "密码不可为空")
//    @Size(min = 8, message = "密码长度不能小于8位")
    private String password;

    @Schema(name = "newPassword", description = "新密码", example = "admin")
    @NotBlank(message = "新密码不可为空")
//    @Size(min = 8, message = "密码长度不能小于8位")
    private String newPassword;

    @Schema(name = "checkPassword", description = "确认密码", example = "admin")
    @NotBlank(message = "确认密码不可为空")
//    @Size(min = 8, message = "密码长度不能小于8位")
    private String checkPassword;

    @Schema(name = "sm", description = "短信验证码", example = "123456")
    private String sms;

    /**
     * sms:短信方式修改
     * old_password:旧密码方式修改
     */
    @Schema(name = "type", description = "类型", example = "1")
    @NotBlank(message = "类型不可为空")
    private String type;
}
