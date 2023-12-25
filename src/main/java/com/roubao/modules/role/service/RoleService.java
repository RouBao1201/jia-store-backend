package com.roubao.modules.role.service;

import com.roubao.common.response.PageList;
import com.roubao.domain.PermissionDO;
import com.roubao.domain.RoleDO;
import com.roubao.modules.role.request.RoleChangedStatusRequest;
import com.roubao.modules.role.request.RolePageQueryRequest;
import com.roubao.modules.role.request.RoleSaveRequest;

import java.util.List;

/**
 * 角色业务接口
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public interface RoleService {

    /**
     * 分页查询角色
     *
     * @param request 请求体
     * @return 角色集合
     */
    PageList<RoleDO> listPage(RolePageQueryRequest request);

    /**
     * 删除角色
     *
     * @param request 请求体
     */
    void changedStatus(RoleChangedStatusRequest request);

    /**
     * 新增角色
     *
     * @param request 请求体
     */
    void saveRole(RoleSaveRequest request);

    /**
     * 获取所有角色
     *
     * @return 角色集合
     */
    List<RoleDO> listAllRole();

    /**
     * 查询角色权限
     *
     * @param id 角色ID
     */
    List<PermissionDO> listRolePermissions(Integer roleId);
}
