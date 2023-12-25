package com.roubao.modules.permission.service;

import com.roubao.domain.PermissionDO;

import java.util.List;

/**
 * @author: SongYanBin
 * @date: 2023-12-25
 */
public interface PermissionService {

    List<PermissionDO> listAllPermissions();
}
