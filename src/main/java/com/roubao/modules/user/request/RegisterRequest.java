package com.roubao.modules.user.request;

import com.roubao.common.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;

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
public class RegisterRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -9118776122854492640L;

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotBlank(message = "用户名不可为空")
    private String username;

    @Schema(name = "password", description = "密码", example = "admin")
    @NotBlank(message = "密码不可为空")
//    @Size(min = 8, message = "密码长度不能小于8位")
    private String password;

    @Schema(name = "checkPassword", description = "确认密码", example = "admin")
    @NotBlank(message = "确认密码不可为空")
//    @Size(min = 8, message = "密码长度不能小于8位")
    private String checkPassword;

    @Schema(name = "email", description = "邮箱", example = "62414****@qq.com")
    @NotBlank(message = "邮箱不可为空")
    private String email;

    @Schema(name = "smsCode", description = "验证码", example = "123456")
    @NotBlank(message = "邮箱不可为空")
    private String smsCode;

//    @Schema(name = "type", description = "类型", example = "1")
//    private Integer type;
}
