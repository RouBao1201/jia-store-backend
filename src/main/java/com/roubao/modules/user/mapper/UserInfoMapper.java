package com.roubao.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.UserInfoPO;
import com.roubao.modules.user.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息Mapper
 *
 * @author: SongYanBin
 * @date: 2023-12-13
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoPO> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT u.id, u.username, ui.email " +
            "FROM user u " +
            "         JOIN user_info ui " +
            "              ON u.id = ui.user_id " +
            "WHERE u.username = #{username}")
    UserInfoDto queryUserInfoByUsername(String username);
}
