package com.roubao.modules.role.request;

import com.roubao.common.request.BaseRequest;
import com.roubao.common.validators.ExistIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * 角色权限修改请求体
 *
 * @author: SongYanBin
 * @date: 2023-12-26
 */
@Schema(name = "角色权限修改请求体", description = "角色权限修改请求体")
@Data
@ToString
public class RolePermissionChangedRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 8432281998662403129L;

    @Schema(name = "roleId", description = "角色ID", example = "1")
    @NotNull(message = "角色ID不可为空")
    private Integer roleId;

    @Schema(name = "permissionIds", description = "权限ID集合", example = "[1,2]")
    private List<Integer> permissionIds;

    @Schema(name = "type", description = "变更类型", example = "SAVE")
    @ExistIn(strRange = {"SAVE", "REMOVE"}, message = "变更类型错误")
    private String type;
}
