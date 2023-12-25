package com.roubao.modules.user.manager;

import com.roubao.domain.UserDO;
import com.roubao.domain.UserInfoDO;
import com.roubao.modules.user.dto.CurrentUserDto;

/**
 * 用户Manager层,用于公共逻辑封装
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
public interface UserManager {
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

    /**
     * 新增用户以及用户信息
     *
     * @param user 用户
     * @return 是否新增成功
     */
    int saveUser(UserDO user, UserInfoDO userInfo);

    /**
     * 根据用户名修改密码
     *
     * @param username 用户名
     * @param password 新密码
     * @return 是否修改成功
     */
    int updatePasswordByUsername(String username, String password);

    /**
     * 根据用户ID修改用户信息
     *
     * @param userinfo 用户信息
     * @return 是否修改成功
     */
    int updateUserInfoById(UserInfoDO userinfo);

    /**
     * 发送邮箱验证码
     *
     * @param email    邮箱
     * @param username 用户名
     */
    String sendEmailCode(String email, String username);

    /**
     * 获取用户的邮箱验证码缓存
     *
     * @param username 用户名
     */
    String getEmailCode(String username);
}
