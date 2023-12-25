package com.roubao.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.UserInfoDO;
import com.roubao.modules.user.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息Mapper
 *
 * @author: SongYanBin
 * @date: 2023-12-13
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoDO> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserInfoDto getUserInfoByUsername(String username);
}
