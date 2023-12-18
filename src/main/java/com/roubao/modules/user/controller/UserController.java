package com.roubao.modules.user.controller;

import com.roubao.common.response.RespResult;
import com.roubao.config.auth.DisableToken;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.LoginReqDto;
import com.roubao.modules.user.dto.LoginRespDto;
import com.roubao.modules.user.dto.PersonalSettingsReqDto;
import com.roubao.modules.user.dto.RegisterReqDto;
import com.roubao.modules.user.dto.ReviseReqDto;
import com.roubao.modules.user.dto.SmsCodeSendReqDto;
import com.roubao.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public RespResult<LoginRespDto> login(@Validated @RequestBody LoginReqDto reqDto) {
        return RespResult.success("登录成功", userService.login(reqDto));
    }

    @Operation(summary = "用户注册", description = "用户注册")
    @DisableToken
    @PostMapping("/register")
    public RespResult<Object> register(@Validated @RequestBody RegisterReqDto reqDto) {
        userService.register(reqDto);
        return RespResult.success("注册成功");
    }

    @Operation(summary = "用户修改密码", description = "用户修改密码")
    @DisableToken
    @PostMapping("/revisePassword")
    public RespResult<Object> revisePassword(@Validated @RequestBody ReviseReqDto reqDto) {
        userService.revisePassword(reqDto);
        return RespResult.success("修改成功");
    }

    @Operation(summary = "个人信息设置", description = "个人信息设置")
    @DisableToken
    @PostMapping("/personalSettings")
    public RespResult<Object> personalSettings(@Validated @RequestBody PersonalSettingsReqDto reqDto) {
        userService.personalSettings(reqDto);
        return RespResult.success("修改成功");
    }

    @Operation(summary = "用户注销", description = "用户注销")
    @PostMapping("/logout")
    public RespResult<Object> logout() {
        userService.logout();
        return RespResult.success("注销成功");
    }

    @Operation(summary = "发送验证码", description = "发送验证码")
    @DisableToken
    @PostMapping("/sendSmsCode")
    public RespResult<Object> sendSmsCode(@Validated @RequestBody SmsCodeSendReqDto reqDto) {
        userService.sendSmsCode(reqDto);
        return RespResult.success("验证码发送成功");
    }

    @Operation(summary = "获取当前用户", description = "获取当前用户")
    @GetMapping("/currentUser")
    public RespResult<CurrentUserDto> getCurrentUser() {
        return RespResult.success(userService.getCurrentUser());
    }
}
