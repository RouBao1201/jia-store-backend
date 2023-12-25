package com.roubao.modules.user.mapper;

import com.roubao.domain.PermissionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限Mapper
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Mapper
public interface PermissionMapper {
    /**
     * 获取所有权限
     *
     * @return 权限列表
     */
    List<PermissionDO> listPermissionByStatus(Integer status);
}
