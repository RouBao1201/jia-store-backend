package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 短信发送请求实体
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Schema(name = "短信发送请求实体", description = "短信发送请求实体")
@Data
@ToString
public class SmsCodeSendReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5484032560123984172L;

    @Schema(name = "username", description = "用户名", example = "admin")
    @NotNull(message = "用户名不能为空")
    private String username;

    @Schema(name = "email", description = "邮箱", example = "62414****@qq.com")
    private String email;
}
