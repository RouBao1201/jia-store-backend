package com.roubao.modules.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.PermissionDO;
import com.roubao.domain.RoleDO;
import com.roubao.modules.role.request.RolePageQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {
    /**
     * 分页查询角色
     *
     * @param request 请求体
     * @return 角色列表
     */
    List<RoleDO> listPage(RolePageQueryRequest request);

    /**
     * 查询角色权限
     *
     * @param roleId 角色ID
     * @return 角色权限
     */
    List<PermissionDO> listRolePermissions(@Param("roleId") Integer roleId);
}
