package com.roubao.modules.permission.controller;

import com.roubao.common.response.RespResult;
import com.roubao.domain.PermissionDO;
import com.roubao.modules.permission.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限API
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Tag(name = "权限API", description = "权限API")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Operation(summary = "查询所有权限", description = "查询所有权限")
    @GetMapping("/listAll")
    public RespResult<List<PermissionDO>> listAllPermissions() {
        return RespResult.success(permissionService.listAllPermissions());
    }
}
