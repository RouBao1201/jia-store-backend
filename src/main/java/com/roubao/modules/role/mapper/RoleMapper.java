package com.roubao.modules.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.PermissionPO;
import com.roubao.domain.RolePO;
import com.roubao.modules.role.dto.RolePageQueryReqDto;
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
public interface RoleMapper extends BaseMapper<RolePO> {
    /**
     * 分页查询角色
     *
     * @param reqDto 请求体
     * @return 角色列表
     */
    List<RolePO> listPage(RolePageQueryReqDto reqDto);

    /**
     * 查询角色权限
     *
     * @param roleId 角色ID
     * @return 角色权限
     */
    List<PermissionPO> listRolePermission(@Param("roleId") Integer roleId);
}
