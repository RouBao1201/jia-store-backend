package com.roubao.modules.user.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.common.constants.ErrorCode;
import com.roubao.common.constants.RedisKey;
import com.roubao.config.asserts.Assert;
import com.roubao.config.cache.token.TokenCacheHolder;
import com.roubao.domain.UserDO;
import com.roubao.domain.UserInfoDO;
import com.roubao.helper.RedisHelper;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.UserInfoDto;
import com.roubao.modules.user.manager.UserManager;
import com.roubao.modules.user.mapper.UserInfoMapper;
import com.roubao.modules.user.mapper.UserMapper;
import com.roubao.modules.user.request.LoginRequest;
import com.roubao.modules.user.request.PersonalSettingsRequest;
import com.roubao.modules.user.request.RegisterRequest;
import com.roubao.modules.user.request.ReviseRequest;
import com.roubao.modules.user.request.SmsCodeSendRequest;
import com.roubao.modules.user.response.LoginResponse;
import com.roubao.modules.user.service.UserService;
import com.roubao.util.EitherUtils;
import com.roubao.util.MD5Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


/**
 * 用户业务层
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public LoginResponse login(LoginRequest request) {
        LambdaQueryWrapper<UserDO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserDO::getUserName, request.getUsername());
        userQueryWrapper.eq(UserDO::getPassword, MD5Utils.encrypt(request.getPassword()));
        UserDO userPo = userMapper.selectOne(userQueryWrapper);
        EitherUtils.throwIfNull(userPo, ErrorCode.PARAM_ERROR, "用户名或密码错误！");
        Assert.AUTH_ERROR.throwIf(ObjUtil.notEqual(userPo.getStatus(), UserDO.STATUS_ENABLED), "用户名已失效，请联系管理员！");
        LoginResponse loginRespDto = new LoginResponse();
        String token = TokenCacheHolder.generateTokenAndCache(userPo.getId());
        loginRespDto.setToken(token);
        return loginRespDto;
    }

    @Override
    public void register(RegisterRequest request) {
        // 校验账号是否重复
        LambdaQueryWrapper<UserDO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserDO::getUserName, request.getUsername());
        UserDO userRes = userMapper.selectOne(userQueryWrapper);
        EitherUtils.throwIfNotNull(userRes, ErrorCode.PARAM_ERROR, "用户名已存在！");

        // 校验邮箱是否重复
        LambdaQueryWrapper<UserInfoDO> userInfoQueryWrapper = new LambdaQueryWrapper<>();
        userInfoQueryWrapper.eq(UserInfoDO::getEmail, request.getEmail());
        UserInfoDO userInfoRes = userInfoMapper.selectOne(userInfoQueryWrapper);
        EitherUtils.throwIfNotNull(userInfoRes, ErrorCode.PARAM_ERROR, "该邮箱已存在注册账户！");

        // 校验验证码是否正确
        String cacheSmsCode = RedisHelper.get(RedisKey.PREFIX_USER_SMS_CODE + request.getUsername(), String.class);
        EitherUtils.throwIf(!StrUtil.equals(cacheSmsCode, request.getSmsCode()), ErrorCode.PARAM_ERROR, "验证码错误或过期！");

        // 新增用户
        UserDO userPo = new UserDO();
        userPo.setUserName(request.getUsername());
        userPo.setPassword(MD5Utils.encrypt(request.getPassword()));
        userPo.setStatus(UserDO.STATUS_ENABLED);
        userPo.setCreateTime(new Date());
        userPo.setUpdateTime(new Date());
        // 新增用户信息（默认数据）
        UserInfoDO userInfo = new UserInfoDO();
        userInfo.setUserId(userPo.getId());
        userInfo.setNickname(userPo.getId() + ":" + RandomUtil.randomString(10));
        userInfo.setGender(1);
        userInfo.setAvatar("");
        userInfo.setEmail(request.getEmail());
        userInfo.setUpdateTime(new Date());
        userInfo.setCreateTime(new Date());
        RedisHelper.delete(RedisKey.PREFIX_USER_SMS_CODE + request.getUsername());
        userManager.saveUser(userPo, userInfo);
    }

    @Override
    public void revisePassword(ReviseRequest request) {
        EitherUtils.throwIf(!Objects.equals(request.getNewPassword(), request.getCheckPassword()), ErrorCode.PARAM_ERROR, "两次密码不一致！");
        LambdaQueryWrapper<UserDO> userQueryWrapper = new LambdaQueryWrapper<>();
        // 输入旧密码方式修改密码
        if (ReviseRequest.TYPE_OLD_PASSWORD.equals(request.getType())) {
            EitherUtils.throwIf(StrUtil.isBlank(request.getOldPassword()), ErrorCode.PARAM_ERROR, "旧密码不可为空！");
            userQueryWrapper.eq(UserDO::getPassword, MD5Utils.encrypt(request.getOldPassword()));
        }
        userQueryWrapper.eq(UserDO::getUserName, request.getUsername());
        UserDO user = userMapper.selectOne(userQueryWrapper);
        EitherUtils.throwIfNull(user, ErrorCode.PARAM_ERROR, "用户名或密码错误！");

        // 短信方式修改密码
        if (ReviseRequest.TYPE_SMS_CODE.equals(request.getType())) {
            String cacheSmsCode = userManager.getEmailCode(request.getUsername());
            EitherUtils.throwIf(!StrUtil.equals(request.getSmsCode(), cacheSmsCode), ErrorCode.PARAM_ERROR, "验证码错误或过期！");
            RedisHelper.delete(RedisKey.PREFIX_USER_SMS_CODE + request.getUsername());
        }

        // 修改密码
        userManager.updatePasswordByUsername(request.getUsername(), request.getNewPassword());
    }

    @Override
    public void personalSettings(PersonalSettingsRequest request) {
        UserDO user = userMapper.selectById(request.getId());
        EitherUtils.throwIfNull(user, ErrorCode.PARAM_ERROR, "用户名不存在！");

        // 校验修改的是否为当前登陆人自己的信息
        boolean currentLoginUser = TokenCacheHolder.isCurrentLoginUser(user.getId());
        EitherUtils.throwIf(!currentLoginUser, ErrorCode.PARAM_ERROR, "当前修改的并非登陆人信息！");

        // 校验邮箱是否存在
        LambdaQueryWrapper<UserInfoDO> userInfoQueryWrapper = new LambdaQueryWrapper<>();
        userInfoQueryWrapper.eq(UserInfoDO::getUserId, request.getId());
        UserInfoDO userInfoPo = userInfoMapper.selectOne(userInfoQueryWrapper);
        // 不相等说明用户修改了邮箱
        if (!StrUtil.equals(request.getEmail(), userInfoPo.getEmail())) {
            LambdaQueryWrapper<UserInfoDO> wrapper = new LambdaQueryWrapper<>();
            userInfoQueryWrapper.eq(UserInfoDO::getEmail, request.getEmail());
            boolean emailExisted = userInfoMapper.exists(wrapper);
            EitherUtils.throwIf(emailExisted, ErrorCode.PARAM_ERROR, "当前邮箱已被注册！");
        }
        String cacheSmsCode = RedisHelper.get(RedisKey.PREFIX_USER_SMS_CODE + request.getUsername(), String.class);
        EitherUtils.throwIf(!StrUtil.equals(request.getSmsCode(), cacheSmsCode), ErrorCode.PARAM_ERROR, "验证码不正确！");

        // 清理用户缓存信息
        RedisHelper.delete(RedisKey.PREFIX_USER_SMS_CODE + request.getUsername());
        userManager.invalidateUserCache(user.getId());
        // 修改用户信息
        UserInfoDO userInfo = new UserInfoDO();
        userInfo.setUserId(request.getId());
        userInfo.setNickname(request.getNickname());
        userInfo.setGender(request.getGender());
        userInfo.setUpdateTime(new Date());
        userInfo.setEmail(request.getEmail());
        userManager.updateUserInfoById(userInfo);
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) {
        TokenCacheHolder.cleanCurrentUserCache(httpServletRequest);
        userManager.invalidateUserCache(TokenCacheHolder.getCurrentUserId(httpServletRequest));
    }

    @Override
    public CurrentUserDto getCurrentUser(HttpServletRequest... httpServletRequests) {
        Integer currentUserId = TokenCacheHolder.getCurrentUserId(httpServletRequests);
        EitherUtils.throwIfNull(currentUserId, ErrorCode.AUTH_ERROR, "用户登录状态已失效！");
        CurrentUserDto user = userManager.getUserById(currentUserId);
        EitherUtils.throwIfNull(user, ErrorCode.AUTH_ERROR, "用户不存在！");
        return user;
    }

    @Override
    public void sendSmsCode(SmsCodeSendRequest request) {
        String email = request.getEmail();
        // 若是修改密码则需要验证用户名
        if (email == null) {
            UserInfoDto userInfoDto = userInfoMapper.getUserInfoByUsername(request.getUsername());
            EitherUtils.throwIfNull(userInfoDto, ErrorCode.PARAM_ERROR, "用户名不存在！");
            email = userInfoDto.getEmail();
        }
        userManager.sendEmailCode(email, request.getUsername());
    }
}
