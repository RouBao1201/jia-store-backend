package com.roubao.modules.permission.controller;

import com.roubao.common.response.RespResult;
import com.roubao.domain.PermissionDO;
import com.roubao.modules.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/listAll")
    public RespResult<List<PermissionDO>> listAllPermissions() {
        return RespResult.success(permissionService.listAllPermissions());
    }
}
