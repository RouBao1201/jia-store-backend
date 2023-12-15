package com.roubao.modules.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.roubao.config.exception.AuthException;
import com.roubao.config.exception.ParameterCheckException;
import com.roubao.config.token.TokenCacheHolder;
import com.roubao.domian.AuthorityPO;
import com.roubao.domian.UserInfoPO;
import com.roubao.domian.UserPO;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.LoginReqDto;
import com.roubao.modules.user.dto.LoginRespDto;
import com.roubao.modules.user.dto.PersonalSettingsReqDto;
import com.roubao.modules.user.dto.RegisterReqDto;
import com.roubao.modules.user.dto.ReviseReqDto;
import com.roubao.modules.user.dto.SmsCodeSendReqDto;
import com.roubao.modules.user.mapper.UserInfoMapper;
import com.roubao.modules.user.mapper.UserMapper;
import com.roubao.modules.user.service.UserCacheService;
import com.roubao.modules.user.service.UserService;
import com.roubao.utils.MD5Util;
import com.roubao.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


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

    private static final String USER_LOGIN_STATE = "USER_LOGIN_STATE";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private TokenCacheHolder tokenCacheHolder;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public LoginRespDto login(LoginReqDto reqDto) {
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        userQueryWrapper.eq(UserPO::getPassword, MD5Util.encrypt(reqDto.getPassword()));
        userQueryWrapper.eq(UserPO::getStatus, 1);
        UserPO userPo = userMapper.selectOne(userQueryWrapper);
        if (userPo == null) {
            throw new ParameterCheckException("用户名或密码错误！");
        }
        LoginRespDto loginRespDto = new LoginRespDto();
        String token = tokenCacheHolder.generateTokenAndPutAtom(userPo.getId());
        loginRespDto.setToken(token);
        SessionUtil.setAttribute(USER_LOGIN_STATE, userPo.getId());
        return loginRespDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterReqDto reqDto) {
        // 校验账号是否重复
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO userRes = userMapper.selectOne(userQueryWrapper);
        if (userRes != null) {
            throw new ParameterCheckException("用户名已存在！");
        }

        // 校验手机号码是否重复
        LambdaQueryWrapper<UserInfoPO> userInfoQueryWrapper = new LambdaQueryWrapper<>();
        userInfoQueryWrapper.eq(UserInfoPO::getPhone, reqDto.getPhone());
        UserInfoPO userInfoRes = userInfoMapper.selectOne(userInfoQueryWrapper);
        if (userInfoRes != null) {
            throw new ParameterCheckException("该号码已存在注册账户！");
        }

        // 新增用户
        UserPO userPo = new UserPO();
        userPo.setUserName(reqDto.getUsername());
        userPo.setPassword(MD5Util.encrypt(reqDto.getPassword()));
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
        userInfo.setPhone(reqDto.getPhone());
        userInfo.setUpdateTime(new Date());
        userInfo.setCreateTime(new Date());
        userInfoMapper.insert(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revisePassword(ReviseReqDto reqDto) {
        if (!Objects.equals(reqDto.getNewPassword(), reqDto.getCheckPassword())) {
            throw new ParameterCheckException("两次密码不一致！");
        }
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        if (ReviseReqDto.TYPE_OLD_PASSWORD.equals(reqDto.getType())) {
            if (StrUtil.isBlank(reqDto.getOldPassword())) {
                throw new ParameterCheckException("旧密码不可为空！");
            }
            userQueryWrapper.eq(UserPO::getPassword, MD5Util.encrypt(reqDto.getOldPassword()));
        } else {
            // todo 验证短信验证码是否正确（当前写死666）
            if (!"666".equals(reqDto.getSmsCode())) {
                throw new ParameterCheckException("验证码不正确！");
            }
        }
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new ParameterCheckException("用户名或密码错误！");
        }

        // 修改密码
        LambdaUpdateWrapper<UserPO> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO userPo = new UserPO();
        userPo.setPassword(MD5Util.encrypt(reqDto.getNewPassword()));
        userPo.setUpdateTime(new Date());
        userMapper.update(userPo, userUpdateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void personalSettings(PersonalSettingsReqDto reqDto) {
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new ParameterCheckException("用户名不存在！");
        }
        // 校验修改的是否为当前登陆人自己的信息
        boolean currentLoginUser = isCurrentLoginUser(user.getId());
        if (!currentLoginUser) {
            throw new ParameterCheckException("不允许修改他人用户信息");
        }

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
        String token = Objects.requireNonNull(SessionUtil.getRequest()).getHeader(TokenCacheHolder.TOKEN_HEADER_KEY);
        tokenCacheHolder.invalidate(token);
        SessionUtil.removeAttribute(USER_LOGIN_STATE);
    }

    @Override
    public CurrentUserDto getCurrentUser() {
        Integer currentUserId = tokenCacheHolder.getCurrentUserId();
        if (currentUserId == null) {
            throw new AuthException("用户登录状态已失效！");
        }
        CurrentUserDto user = userCacheService.getUserById(currentUserId);
        if (user == null) {
            throw new AuthException("用户不存在！");
        }
        return user;
    }

    @Override
    public boolean isSuperAdmin(Integer userId) {
        Integer superAdmin = userMapper.isSuperAdmin(userId);
        return superAdmin != null;
    }

    @Override
    public void sendSmsCode(SmsCodeSendReqDto reqDto) {
        // TODO 发送验证码
    }

    @Override
    public Map<String, AuthorityPO> getUserAuthority(Integer userId) {
        List<AuthorityPO> authorityList = userMapper.queryUserAuthority(userId);
        if (CollectionUtil.isNotEmpty(authorityList)) {
            return authorityList.stream().collect(Collectors.toMap(AuthorityPO::getAuthKey, Function.identity()));
        }
        return new HashMap<>();
    }

    @Override
    public boolean isCurrentLoginUser(Integer userId) {
        return ObjUtil.equals(tokenCacheHolder.getCurrentUserId(), userId);
    }
}
