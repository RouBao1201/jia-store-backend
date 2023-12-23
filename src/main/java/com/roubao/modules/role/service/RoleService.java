package com.roubao.modules.role.service;

import com.roubao.common.response.PageList;
import com.roubao.domain.RolePO;
import com.roubao.modules.role.dto.RoleChangedStatusReqDto;
import com.roubao.modules.role.dto.RolePageQueryReqDto;
import com.roubao.modules.role.dto.RoleSaveReqDto;

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
     * @param reqDto 请求体
     * @return 角色集合
     */
    PageList<RolePO> listPage(RolePageQueryReqDto reqDto);

    /**
     * 删除角色
     *
     * @param reqDto 请求体
     */
    void changedStatus(RoleChangedStatusReqDto reqDto);

    /**
     * 新增角色
     *
     * @param reqDto 请求体
     */
    void saveRole(RoleSaveReqDto reqDto);
}