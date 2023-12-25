package com.roubao.modules.user.request;

import com.roubao.common.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;

/**
 * 用户登录请求实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/9
 **/
@Schema(name = "用户登录请求实体", description = "用户登录请求实体")
@Data
@ToString
public class LoginRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -9170272664410263491L;

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(name = "password", description = "密码", example = "admin")
    @NotBlank(message = "用户名不能为空")
//    @Size(min = 8, message = "密码长度不能小于8位")
    private String password;

//    @Schema(name = "type", description = "类型", example = "1")
//    @NotNull(message = "类型不能为空")
//    @ExistIn(intRange = {1}, message = "类型不在可选范围内")
//    private Integer type;
}
