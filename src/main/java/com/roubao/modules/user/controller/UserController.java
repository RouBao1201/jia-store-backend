package com.roubao.modules.user.controller;

import com.roubao.common.response.RespResult;
import com.roubao.config.auth.DisableToken;
import com.roubao.modules.user.dto.LoginReqDto;
import com.roubao.modules.user.dto.LoginRespDto;
import com.roubao.modules.user.dto.RegisterReqDto;
import com.roubao.modules.user.dto.RegisterRespDto;
import com.roubao.modules.user.dto.ReviseReqDto;
import com.roubao.modules.user.dto.ReviseRespDto;
import com.roubao.modules.user.service.UserService;
import com.roubao.modules.user.dto.CurrentUserDto;
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
    public RespResult<LoginRespDto> login(@Validated @RequestBody LoginReqDto reqDto) {
        return RespResult.success("登录成功", userService.login(reqDto));
    }

    @Operation(summary = "用户注册", description = "用户注册")
    @DisableToken
    @PostMapping("/register")
    public RespResult<RegisterRespDto> register(@Validated @RequestBody RegisterReqDto reqDto) {
        RegisterRespDto registerRespDto = new RegisterRespDto();
        registerRespDto.setStatus(RegisterRespDto.transStatus(userService.register(reqDto)));
        return RespResult.success("注册成功", registerRespDto);
    }

    @Operation(summary = "用户修改密码", description = "用户修改密码")
    @DisableToken
    @PostMapping("/revise")
    public RespResult<ReviseRespDto> revise(@Validated @RequestBody ReviseReqDto reqDto) {
        ReviseRespDto reviseRespDto = new ReviseRespDto();
        reviseRespDto.setStatus(ReviseRespDto.transStatus(userService.revise(reqDto)));
        return RespResult.success("注册成功", reviseRespDto);
    }

    @Operation(summary = "用户注销", description = "用户注销")
    @PostMapping("/logout")
    public RespResult<Object> logout() {
        userService.logout();
        return RespResult.success("注销成功");
    }

    @Operation(summary = "获取当前用户", description = "获取当前用户")
    @GetMapping("/currentUser")
    public RespResult<CurrentUserDto> getCurrentUser(HttpServletRequest request) {
        return RespResult.success(userService.getCurrentUser());
    }
}
