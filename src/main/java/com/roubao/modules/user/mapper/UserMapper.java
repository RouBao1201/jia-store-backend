package com.roubao.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.AuthorityPO;
import com.roubao.domain.RolePO;
import com.roubao.domain.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT 1 " +
            "FROM user u " +
            "         JOIN user_role ur ON u.id = ur.user_id " +
            "         JOIN role r ON ur.role_id = r.id " +
            "WHERE r.name = 'SuperAdmin' " +
            "  AND u.id = #{userId} " +
            "  AND u.status = 1 " +
            "  AND r.status = 1 " +
            "LIMIT 1 ")
    Integer isSuperAdmin(Integer userId);

    /**
     * 查询用户权限
     *
     * @param userId 用户ID
     * @return 用户权限集合
     */
    @Select("SELECT DISTINCT a.id, a.auth_key, a.name, a.type, a.status " +
            "FROM user u " +
            "         JOIN user_role ur ON u.id = ur.user_id " +
            "         JOIN role r ON ur.role_id = r.id " +
            "         JOIN role_authority ra ON r.id = ra.role_id " +
            "         JOIN authority a ON a.id = ra.authority_id " +
            "WHERE u.id = #{userId} " +
            "  AND u.status = 1 " +
            "  AND r.status = 1 " +
            "  AND a.status = 1 ")
    List<AuthorityPO> queryUserAuthority(Integer userId);

    /**
     * 查询用户所有角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    @Select("SELECT r.id, r.name, r.status, r.create_time, r.update_time " +
            "FROM user u " +
            "         JOIN user_role ur ON u.id = ur.user_id " +
            "         JOIN role r ON r.id = ur.role_id " +
            "WHERE u.status = 1 " +
            "  AND r.status = 1 ")
    List<RolePO> queryUserRole(Integer userId);
}