package com.roubao.modules.user.service;

import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.request.LoginRequest;
import com.roubao.modules.user.response.LoginResponse;
import com.roubao.modules.user.request.PersonalSettingsRequest;
import com.roubao.modules.user.request.RegisterRequest;
import com.roubao.modules.user.request.ReviseRequest;
import com.roubao.modules.user.request.SmsCodeSendRequest;
import jakarta.servlet.http.HttpServletRequest;

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
     * @param request 登录请求体
     * @return 登录响应体
     */
    LoginResponse login(LoginRequest request);

    /**
     * 用户注册
     *
     * @param request 注册请求体
     */
    void register(RegisterRequest request);

    /**
     * 用户修改密码（短信）
     *
     * @param request 修改请求体
     */
    void revisePassword(ReviseRequest request);

    /**
     * 个人信息设置
     *
     * @param reqDto 修改请求体
     */
    void personalSettings(PersonalSettingsRequest reqDto);

    /**
     * 用户注销
     *
     * @return 注销是否成功
     */
    void logout(HttpServletRequest httpServletRequest);

    /**
     * 获取当前用户
     *
     * @return 当前用户信息
     */
    CurrentUserDto getCurrentUser(HttpServletRequest... httpServletRequest);

    /**
     * 短信验证码发送
     *
     * @param request 验证码发送请求体
     */
    void sendSmsCode(SmsCodeSendRequest request);
}
