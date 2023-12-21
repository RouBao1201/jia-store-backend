package com.roubao.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.AuthorityPO;
import com.roubao.domain.RolePO;
import com.roubao.domain.UserPO;
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
public interface UserMapper extends BaseMapper<UserPO> {

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
    List<AuthorityPO> listUserAuthority(Integer userId);

    /**
     * 查询用户所有角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<RolePO> listUserRole(Integer userId);
}