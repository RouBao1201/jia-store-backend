package com.roubao.modules.user.dto;

import com.roubao.common.validators.ExistIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class PersonalSettingsReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1264095329488043275L;

    @Schema(name = "nickname", description = "昵称", example = "admin")
    @NotBlank(message = "昵称不可为空")
    private String nickname;

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
