package com.roubao.modules.role.dto;

import com.roubao.common.validators.ExistIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色新增请求实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Schema(name = "角色新增请求实体", description = "角色新增请求实体")
@Data
@ToString
public class RoleSaveReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -876801038838898761L;

    @Schema(name = "name", description = "角色名称", example = "管理员")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Schema(name = "status", description = "状态", example = "0")
    @ExistIn(intRange = {0, 1}, message = "状态值不正确")
    private Integer status;
}
