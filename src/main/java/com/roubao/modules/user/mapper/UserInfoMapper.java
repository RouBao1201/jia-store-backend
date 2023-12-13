package com.roubao.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domian.UserInfoPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息Mapper
 *
 * @author: SongYanBin
 * @date: 2023-12-13
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoPO> {
}
