package com.roubao.modules.user.controller;

import com.roubao.common.response.RespResult;
import com.roubao.config.auth.DisableToken;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.request.LoginRequest;
import com.roubao.modules.user.response.LoginResponse;
import com.roubao.modules.user.request.PersonalSettingsRequest;
import com.roubao.modules.user.request.RegisterRequest;
import com.roubao.modules.user.request.ReviseRequest;
import com.roubao.modules.user.request.SmsCodeSendRequest;
import com.roubao.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户API
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/9
 **/
@Tag(name = "用户API", description = "用户API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录", description = "用户登录")
    @DisableToken
    @PostMapping("/login")
    public RespResult<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        return RespResult.success("登录成功", userService.login(request));
    }

    @Operation(summary = "用户注册", description = "用户注册")
    @DisableToken
    @PostMapping("/register")
    public RespResult<Object> register(@Validated @RequestBody RegisterRequest request) {
        userService.register(request);
        return RespResult.success("注册成功");
    }

    @Operation(summary = "用户修改密码", description = "用户修改密码")
    @DisableToken
    @PostMapping("/revisePassword")
    public RespResult<Object> revisePassword(@Validated @RequestBody ReviseRequest request) {
        userService.revisePassword(request);
        return RespResult.success("修改成功");
    }

    @Operation(summary = "个人信息设置", description = "个人信息设置")
    @PostMapping("/personalSettings")
    public RespResult<Object> personalSettings(@Validated @RequestBody PersonalSettingsRequest request) {
        userService.personalSettings(request);
        return RespResult.success("修改成功");
    }

    @Operation(summary = "用户注销", description = "用户注销")
    @PostMapping("/logout")
    public RespResult<Object> logout(HttpServletRequest httpServletRequest) {
        userService.logout(httpServletRequest);
        return RespResult.success("注销成功");
    }

    @Operation(summary = "发送验证码", description = "发送验证码")
    @DisableToken
    @PostMapping("/sendSmsCode")
    public RespResult<Object> sendSmsCode(@Validated @RequestBody SmsCodeSendRequest request) {
        userService.sendSmsCode(request);
        return RespResult.success("验证码发送成功");
    }

    @Operation(summary = "获取当前用户", description = "获取当前用户")
    @GetMapping("/currentUser")
    public RespResult<CurrentUserDto> getCurrentUser(HttpServletRequest httpServletRequest) {
        return RespResult.success(userService.getCurrentUser(httpServletRequest));
    }
}
