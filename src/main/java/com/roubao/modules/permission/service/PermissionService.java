package com.roubao.modules.permission.service;

import com.roubao.domain.PermissionDO;

import java.util.List;

/**
 * 权限业务接口
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
public interface PermissionService {

    /**
     * 查询所有权限
     *
     * @return 权限集合
     */
    List<PermissionDO> listAllPermissions();
}
