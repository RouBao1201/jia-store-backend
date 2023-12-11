package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色实体
 *
 * @author: SongYanBin
 * @date: 2023-12-11
 */
@Schema(name = "用户角色实体", description = "用户角色实体")
@Data
@ToString
public class UserRoleDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -3456604997091135469L;

    @Schema(name = "id", description = "用户角色实体")
    private Integer id;

    @Schema(name = "name", description = "角色名称")
    private String name;

    @Schema(name = "status", description = "角色状态")
    private Integer status;
}
