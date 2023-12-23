package com.roubao.modules.role.dto;

import com.roubao.common.request.PageReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色分页查询请求实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Schema(name = "角色分页查询请求实体", description = "角色分页查询请求实体")
@Data
@ToString
public class RolePageQueryReqDto extends PageReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6716648319024090323L;

    @Schema(name = "name", description = "角色名称", example = "admin")
    private String name;

    @Schema(name = "status", description = "角色状态", example = "1")
    private Integer status;
}
