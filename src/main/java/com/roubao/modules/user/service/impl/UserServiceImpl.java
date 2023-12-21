package com.roubao.modules.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.roubao.common.constants.ErrorCode;
import com.roubao.common.constants.RedisKey;
import com.roubao.config.cache.token.TokenCacheHolder;
import com.roubao.domain.UserInfoPO;
import com.roubao.domain.UserPO;
import com.roubao.helper.EmailHelper;
import com.roubao.helper.RedisHelper;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.LoginReqDto;
import com.roubao.modules.user.dto.LoginRespDto;
import com.roubao.modules.user.dto.PersonalSettingsReqDto;
import com.roubao.modules.user.dto.RegisterReqDto;
import com.roubao.modules.user.dto.ReviseReqDto;
import com.roubao.modules.user.dto.SmsCodeSendReqDto;
import com.roubao.modules.user.dto.UserInfoDto;
import com.roubao.modules.user.mapper.UserInfoMapper;
import com.roubao.modules.user.mapper.UserMapper;
import com.roubao.modules.user.service.UserCacheService;
import com.roubao.modules.user.service.UserService;
import com.roubao.util.EitherUtils;
import com.roubao.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * 用户业务层
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public LoginRespDto login(LoginReqDto reqDto) {
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        userQueryWrapper.eq(UserPO::getPassword, MD5Utils.encrypt(reqDto.getPassword()));
        userQueryWrapper.eq(UserPO::getStatus, 1);
        UserPO userPo = userMapper.selectOne(userQueryWrapper);
        EitherUtils.throwIfNull(userPo, ErrorCode.PARAM_ERROR, "用户名或密码错误！");
        LoginRespDto loginRespDto = new LoginRespDto();
        String token = TokenCacheHolder.generateTokenAndCache(userPo.getId());
        loginRespDto.setToken(token);
        return loginRespDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterReqDto reqDto) {
        // 校验账号是否重复
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO userRes = userMapper.selectOne(userQueryWrapper);
        EitherUtils.throwIfNotNull(userRes, ErrorCode.PARAM_ERROR, "用户名已存在！");

        // 校验邮箱是否重复
        LambdaQueryWrapper<UserInfoPO> userInfoQueryWrapper = new LambdaQueryWrapper<>();
        userInfoQueryWrapper.eq(UserInfoPO::getEmail, reqDto.getEmail());
        UserInfoPO userInfoRes = userInfoMapper.selectOne(userInfoQueryWrapper);
        EitherUtils.throwIfNotNull(userInfoRes, ErrorCode.PARAM_ERROR, "该邮箱已存在注册账户！");

        // 校验验证码是否正确
        String cacheSmsCode = RedisHelper.get(RedisKey.PREFIX_USER_SMS_CODE + reqDto.getUsername(), String.class);
        EitherUtils.throwIf(!StrUtil.equals(cacheSmsCode, reqDto.getSmsCode()), ErrorCode.PARAM_ERROR, "验证码错误或过期！");

        // 新增用户
        UserPO userPo = new UserPO();
        userPo.setUserName(reqDto.getUsername());
        userPo.setPassword(MD5Utils.encrypt(reqDto.getPassword()));
        userPo.setStatus(UserPO.STATUS_ENABLED);
        userPo.setCreateTime(new Date());
        userPo.setUpdateTime(new Date());
        userMapper.insert(userPo);

        // 新增用户信息（默认数据）
        UserInfoPO userInfo = new UserInfoPO();
        userInfo.setUserId(userPo.getId());
        userInfo.setNickname(userPo.getId() + ":" + RandomUtil.randomString(10));
        userInfo.setGender(1);
        userInfo.setAvatar("");
        userInfo.setEmail(reqDto.getEmail());
        userInfo.setUpdateTime(new Date());
        userInfo.setCreateTime(new Date());
        userInfoMapper.insert(userInfo);
        RedisHelper.delete(RedisKey.PREFIX_USER_SMS_CODE + reqDto.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revisePassword(ReviseReqDto reqDto) {
        EitherUtils.throwIf(!Objects.equals(reqDto.getNewPassword(), reqDto.getCheckPassword()), ErrorCode.PARAM_ERROR, "两次密码不一致！");
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        // 输入旧密码方式修改密码
        if (ReviseReqDto.TYPE_OLD_PASSWORD.equals(reqDto.getType())) {
            EitherUtils.throwIf(StrUtil.isBlank(reqDto.getOldPassword()), ErrorCode.PARAM_ERROR, "旧密码不可为空！");
            userQueryWrapper.eq(UserPO::getPassword, MD5Utils.encrypt(reqDto.getOldPassword()));
        }
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO user = userMapper.selectOne(userQueryWrapper);
        EitherUtils.throwIfNull(user, ErrorCode.PARAM_ERROR, "用户名或密码错误！");

        // 短信方式修改密码
        if (ReviseReqDto.TYPE_SMS_CODE.equals(reqDto.getType())) {
            String cacheSmsCode = RedisHelper.get(RedisKey.PREFIX_USER_SMS_CODE + reqDto.getUsername(), String.class);
            EitherUtils.throwIf(!StrUtil.equals(reqDto.getSmsCode(), cacheSmsCode), ErrorCode.PARAM_ERROR, "验证码错误或过期！");
            RedisHelper.delete(RedisKey.PREFIX_USER_SMS_CODE + reqDto.getUsername());
        }

        // 修改密码
        LambdaUpdateWrapper<UserPO> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO userPo = new UserPO();
        userPo.setPassword(MD5Utils.encrypt(reqDto.getNewPassword()));
        userPo.setUpdateTime(new Date());
        userMapper.update(userPo, userUpdateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void personalSettings(PersonalSettingsReqDto reqDto) {
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO user = userMapper.selectOne(userQueryWrapper);
        EitherUtils.throwIfNull(user, ErrorCode.PARAM_ERROR, "用户名不存在！");

        // 校验修改的是否为当前登陆人自己的信息
        boolean currentLoginUser = TokenCacheHolder.isCurrentLoginUser(user.getId());
        EitherUtils.throwIf(!currentLoginUser, ErrorCode.PARAM_ERROR, "不允许修改他人用户信息！");

        // 修改用户信息
        LambdaUpdateWrapper<UserInfoPO> userInfoUpdateWrapper = new LambdaUpdateWrapper<>();
        userInfoUpdateWrapper.eq(UserInfoPO::getUserId, user.getId());
        UserInfoPO userInfo = new UserInfoPO();
        userInfo.setNickname(reqDto.getNickname());
        userInfo.setGender(reqDto.getGender());
        userInfo.setUpdateTime(new Date());
        userInfoMapper.update(userInfo, userInfoUpdateWrapper);
        // 清理用户缓存信息
        userCacheService.invalidateUserCache(user.getId());
    }

    @Override
    public void logout() {
        userCacheService.invalidateUserCache(TokenCacheHolder.getCurrentUserId());
        TokenCacheHolder.cleanCurrentUserCache();
    }

    @Override
    public CurrentUserDto getCurrentUser() {
        Integer currentUserId = TokenCacheHolder.getCurrentUserId();
        EitherUtils.throwIfNull(currentUserId, ErrorCode.AUTH_ERROR, "用户登录状态已失效！");
        CurrentUserDto user = userCacheService.getUserById(currentUserId);
        EitherUtils.throwIfNull(user, ErrorCode.AUTH_ERROR, "用户不存在！");
        return user;
    }

    @Override
    public void sendSmsCode(SmsCodeSendReqDto reqDto) {
        String email = reqDto.getEmail();
        // 若是修改密码则需要验证用户名
        if (reqDto.getEmail() == null) {
            UserInfoDto userInfoDto = userInfoMapper.getUserInfoByUsername(reqDto.getUsername());
            EitherUtils.throwIfNull(userInfoDto, ErrorCode.PARAM_ERROR, "用户名不存在！");
            email = userInfoDto.getEmail();
        }
        // 发送验证码
        String smsCode = RandomUtil.randomNumbers(6);
        EmailHelper.sendSimple(email, "肉包仔", "验证码: " + smsCode);
        RedisHelper.set(RedisKey.PREFIX_USER_SMS_CODE + reqDto.getUsername(), smsCode, 60, TimeUnit.SECONDS);
    }
}
