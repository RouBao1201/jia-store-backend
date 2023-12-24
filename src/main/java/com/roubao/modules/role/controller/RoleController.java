package com.roubao.modules.role.controller;

import com.roubao.common.response.PageResult;
import com.roubao.common.response.RespResult;
import com.roubao.domain.PermissionPO;
import com.roubao.domain.RolePO;
import com.roubao.modules.role.dto.RoleChangedStatusReqDto;
import com.roubao.modules.role.dto.RolePageQueryReqDto;
import com.roubao.modules.role.dto.RolePermissionQueryReqDto;
import com.roubao.modules.role.dto.RoleSaveReqDto;
import com.roubao.modules.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 角色API
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Tag(name = "角色API", description = "角色API")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "分页查询角色", description = "分页查询角色")
    @PostMapping("/listPage")
    public PageResult<RolePO> listPage(@RequestBody RolePageQueryReqDto reqDto) {
        return PageResult.success(roleService.listPage(reqDto));
    }

    @Operation(summary = "修改角色状态", description = "修改角色状态")
    @PutMapping("/changedStatus")
    public RespResult<Objects> changedStatus(@Validated @RequestBody RoleChangedStatusReqDto reqDto) throws InterruptedException {
        roleService.changedStatus(reqDto);
        return RespResult.success();
    }

    @Operation(summary = "新增角色", description = "新增角色")
    @PostMapping("/save")
    public RespResult<Objects> saveRole(@Validated @RequestBody RoleSaveReqDto reqDto) {
        roleService.saveRole(reqDto);
        return RespResult.success();
    }

    @Operation(summary = "查询所有角色", description = "查询所有角色")
    @GetMapping("/listAllRole")
    public RespResult<List<RolePO>> listAllRole() {
        return RespResult.success(roleService.listAllRole());
    }

    @Operation(summary = "查询角色权限", description = "查询角色权限")
    @PostMapping("/listRolePermission")
    public RespResult<List<PermissionPO>> listRolePermission(@Validated @RequestBody RolePermissionQueryReqDto reqDto) {
        return RespResult.success(roleService.listRolePermission(reqDto.getId()));
    }
}
