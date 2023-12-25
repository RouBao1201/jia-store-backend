package com.roubao.modules.user.request;

import com.roubao.common.validators.ExistIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 个人信息修改请求实体
 *
 * @author: SongYanBin
 * @date: 2023-12-15
 */
@Schema(name = "个人信息修改请求实体", description = "个人信息修改请求实体")
@Data
@ToString
public class PersonalSettingsRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1264095329488043275L;

    @Schema(name = "id", description = "用户ID", example = "1")
    @NotNull(message = "用户ID不可为空")
    private Integer id;

    @Schema(name = "nickname", description = "昵称", example = "admin")
    @NotBlank(message = "昵称不可为空")
    private String nickname;

    @Schema(name = "email", description = "邮箱", example = "62414****@qq.com")
    @NotBlank(message = "邮箱不可为空")
    private String email;

    @Schema(name = "smsCode", description = "验证码", example = "123456")
    @NotBlank(message = "验证码不可为空")
    private String smsCode;

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotBlank(message = "用户名不可为空")
    private String username;

    @Schema(name = "gender", description = "性别", example = "1")
    @ExistIn(intRange = {0, 1}, message = "性别不在可选范围内")
    private Integer gender;

//    @Schema(name = "oldPassword", description = "旧密码", example = "admin")
//    @NotBlank(message = "旧密码不可为空")
//    private String oldPassword;
//
//    @Schema(name = "newPassword", description = "新密码", example = "admin")
//    @NotBlank(message = "新密码不可为空")
//    private String newPassword;
//
//    @Schema(name = "checkPassword", description = "确认密码", example = "admin")
//    @NotBlank(message = "确认密码不可为空")
//    private String checkPassword;
}
