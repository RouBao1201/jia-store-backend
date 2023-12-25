package com.roubao.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.PermissionDO;
import com.roubao.domain.RoleDO;
import com.roubao.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户持久层
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 判断是否超级管理员
     *
     * @param userId 用户ID
     * @return Integer
     */
    Integer isSuperAdmin(Integer userId);

    /**
     * 查询用户权限
     *
     * @param userId 用户ID
     * @return 用户权限集合
     */
    List<PermissionDO> listUserPermission(Integer userId);

    /**
     * 查询用户所有角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<RoleDO> listUserRole(Integer userId);
}