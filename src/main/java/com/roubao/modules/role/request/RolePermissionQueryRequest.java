package com.roubao.modules.role.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色权限查询请求实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/24
 **/
@Schema(name = "角色权限查询请求实体", description = "角色权限查询请求实体")
@Data
@ToString
public class RolePermissionQueryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7079016817939936907L;

    @Schema(name = "id", description = "角色ID", example = "1")
    @NotNull(message = "角色ID不可为空")
    private Integer id;
}
