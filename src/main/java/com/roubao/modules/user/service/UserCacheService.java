package com.roubao.modules.user.service;

import com.roubao.modules.user.dto.CurrentUserDto;

/**
 * 用户业务（带缓存）
 *
 * @author: SongYanBin
 * @date: 2023-12-11
 */
public interface UserCacheService {

    /**
     * 根据id获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    CurrentUserDto getUserById(Integer userId);

    /**
     * 清除用户缓存
     *
     * @param userId 用户ID
     */
    void invalidateUserCache(Integer userId);
}
