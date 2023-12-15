package com.roubao.modules.user.dto;

import com.roubao.common.validators.ExistIn;
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

    public static final String TYPE_OLD_PASSWORD = "old_password";
    public static final String TYPE_SMS_CODE = "sms_code";

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotBlank(message = "用户名不可为空")
    private String username;

    @Schema(name = "oldPassword", description = "旧密码", example = "admin")
    private String oldPassword;

    @Schema(name = "newPassword", description = "新密码", example = "admin")
    @NotBlank(message = "新密码不可为空")
    private String newPassword;

    @Schema(name = "checkPassword", description = "确认密码", example = "admin")
    @NotBlank(message = "确认密码不可为空")
    private String checkPassword;

    @Schema(name = "smsCode", description = "短信验证码", example = "123456")
    private String smsCode;

    @Schema(name = "type", description = "类型", example = "old_password/sms_code")
    @ExistIn(strRange = {"old_password", "sms_code"}, message = "类型不在可选范围内")
    private String type;
}
