package com.roubao.modules.user.response;

import com.roubao.common.reponse.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;

/**
 * 登录响应实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/9
 **/
@Schema(name = "用户登录响应实体", description = "用户登录响应实体")
@Data
@ToString
public class LoginResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = -4636356007042837482L;

    @Schema(name = "token", description = "token令牌")
    private String token;
}
