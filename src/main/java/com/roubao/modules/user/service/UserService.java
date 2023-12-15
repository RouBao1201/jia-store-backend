package com.roubao.modules.user.service;

import com.roubao.domian.AuthorityPO;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.LoginReqDto;
import com.roubao.modules.user.dto.LoginRespDto;
import com.roubao.modules.user.dto.PersonalSettingsReqDto;
import com.roubao.modules.user.dto.RegisterReqDto;
import com.roubao.modules.user.dto.ReviseReqDto;
import com.roubao.modules.user.dto.SmsCodeSendReqDto;

import java.util.Map;

/**
 * 用户业务接口
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/9
 **/
public interface UserService {

    /**
     * 用户登录
     *
     * @param reqDto 登录请求体
     * @return 登录响应体
     */
    LoginRespDto login(LoginReqDto reqDto);

    /**
     * 用户注册
     *
     * @param reqDto 注册请求体
     */
    void register(RegisterReqDto reqDto);

    /**
     * 用户修改密码（短信）
     *
     * @param reqDto 修改请求体
     */
    void revisePassword(ReviseReqDto reqDto);

    /**
     * 个人信息设置
     *
     * @param reqDto 修改请求体
     */
    void personalSettings(PersonalSettingsReqDto reqDto);

    /**
     * 用户注销
     *
     * @return 注销是否成功
     */
    void logout();

    /**
     * 获取当前用户
     *
     * @return 当前用户信息
     */
    CurrentUserDto getCurrentUser();

    /**
     * 判断用户是否为管理员
     *
     * @param userId 用户ID
     * @return boolean
     */
    boolean isSuperAdmin(Integer userId);

    /**
     * 短信验证码发送
     *
     * @param reqDto 验证码发送请求体
     */
    void sendSmsCode(SmsCodeSendReqDto reqDto);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 用户权限
     */
    Map<String, AuthorityPO> getUserAuthority(Integer userId);

    /**
     * 校验是否为当前登录用户
     *
     * @param userId 用户ID
     * @return 校验结果
     */
    boolean isCurrentLoginUser(Integer userId);
}
