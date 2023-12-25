package com.roubao.modules.role.request;

import com.roubao.common.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;

/**
 * 角色状态修改请求实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Schema(name = "角色状态修改请求实体", description = "角色状态修改请求实体")
@Data
@ToString
public class RoleChangedStatusRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = -6860978568461922775L;

    @Schema(name = "id", description = "角色ID", example = "1")
    @NotNull(message = "角色ID不可为空")
    private Integer id;

    @Schema(name = "status", description = "状态", example = "1")
    @NotNull(message = "状态")
    private Integer status;
}
