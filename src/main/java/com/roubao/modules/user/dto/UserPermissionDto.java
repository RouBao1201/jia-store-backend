package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户权限实体
 *
 * @author: SongYanBin
 * @date: 2023-12-11
 */
@Schema(name = "用户权限实体", description = "用户权限实体")
@Data
@ToString
public class UserPermissionDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 3164631629192342031L;

    @Schema(name = "id", description = "权限ID")
    private Integer id;

    @Schema(name = "authKey", description = "权限KEY")
    private String authKey;

    @Schema(name = "name", description = "权限名称")
    private String name;

    @Schema(name = "type", description = "权限类型")
    private String type;

    @Schema(name = "status", description = "权限状态")
    private Integer status;
}
