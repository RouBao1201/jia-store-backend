package com.roubao.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 当前用户
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/9
 **/
@Schema(name = "当前用户", description = "当前用户")
@Data
@ToString
public class CurrentUserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -9176014559421190930L;

    @Schema(name = "superAdmin", description = "用户是否为超级管理员")
    private boolean superAdmin;

    @Schema(name = "userInfo", description = "用户信息")
    private UserInfoDto userInfo;

    @Schema(name = "userAuth", description = "用户权限")
    private List<UserAuthorityDto> userAuth;

    @Schema(name = "userRole", description = "用户角色")
    private List<UserRoleDto> userRole;
}
