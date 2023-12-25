package com.roubao.modules.user.manager.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.roubao.common.constants.RedisKey;
import com.roubao.domain.PermissionDO;
import com.roubao.domain.RoleDO;
import com.roubao.domain.UserDO;
import com.roubao.domain.UserInfoDO;
import com.roubao.helper.EmailHelper;
import com.roubao.helper.RedisHelper;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.UserInfoDto;
import com.roubao.modules.user.dto.UserPermissionDto;
import com.roubao.modules.user.dto.UserRoleDto;
import com.roubao.modules.user.manager.UserManager;
import com.roubao.modules.user.mapper.PermissionMapper;
import com.roubao.modules.user.mapper.UserInfoMapper;
import com.roubao.modules.user.mapper.UserMapper;
import com.roubao.util.EitherUtils;
import com.roubao.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户业务管理层
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Service
public class UserManagerImpl implements UserManager {
    private final static Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    private static final String CURRENT_USER_CACHE_PREFIX = "user:currentUser:";

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Cacheable(cacheNames = "user", key = "'" + CURRENT_USER_CACHE_PREFIX + "'" + "+ #userId", unless = "#result == null")
    public CurrentUserDto getUserById(Integer userId) {
        UserDO user = userMapper.selectById(userId);
        if (user == null || !Objects.equals(user.getStatus(), UserDO.STATUS_ENABLED)) {
            return null;
        }
        CurrentUserDto currentUserDto = new CurrentUserDto();
        List<UserPermissionDto> userAuthList = new ArrayList<>();
        Integer superAdmin = userMapper.isSuperAdmin(userId);
        EitherUtils.boolE(superAdmin != null).either(() -> {
            currentUserDto.setSuperAdmin(true);
            // 超级管理员获取所有权限
            List<PermissionDO> permissionList = permissionMapper.listPermissionsByStatus(null);
            if (CollUtil.isNotEmpty(permissionList)) {
                permissionList.forEach(it -> {
                    UserPermissionDto dto = new UserPermissionDto();
                    BeanUtil.copyProperties(it, dto);
                    userAuthList.add(dto);
                });
            }
        }, () -> {
            currentUserDto.setSuperAdmin(false);
            // 用户权限
            List<PermissionDO> permissionList = userMapper.listUserPermission(userId);
            if (CollUtil.isNotEmpty(permissionList)) {
                permissionList.forEach(it -> {
                    UserPermissionDto dto = new UserPermissionDto();
                    BeanUtil.copyProperties(it, dto);
                    userAuthList.add(dto);
                });
            }
            // 用户角色
            List<RoleDO> roleList = userMapper.listUserRole(userId);
            List<UserRoleDto> userRoleList = new ArrayList<>();
            if (CollUtil.isNotEmpty(roleList)) {
                roleList.forEach(it -> {
                    UserRoleDto dto = new UserRoleDto();
                    BeanUtil.copyProperties(it, dto);
                    userRoleList.add(dto);
                });
            }
            currentUserDto.setUserRole(userRoleList);
        });
        currentUserDto.setUserAuth(userAuthList);

        // 用户信息
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userId);
        userInfoDto.setStatus(user.getStatus());
        userInfoDto.setUsername(user.getUserName());
        UserInfoDO userInfo = userInfoMapper.selectById(userId);
        if (userInfo != null) {
            userInfoDto.setGender(userInfo.getGender());
            userInfoDto.setAvatar(userInfo.getAvatar());
            userInfoDto.setNickname(userInfo.getNickname());
            userInfoDto.setEmail(userInfo.getEmail());
        }
        currentUserDto.setUserInfo(userInfoDto);
        return currentUserDto;
    }

    @Override
    @CacheEvict(cacheNames = "user", key = "'" + CURRENT_USER_CACHE_PREFIX + "'" + "+ #userId")
    public void invalidateUserCache(Integer userId) {
        logger.info("清除用户缓存(下线):{}", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveUser(UserDO user, UserInfoDO userInfo) {
        userInfoMapper.insert(userInfo);
        return userMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePasswordByUsername(String username, String password) {
        LambdaUpdateWrapper<UserDO> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.eq(UserDO::getUserName, username);
        UserDO userPo = new UserDO();
        userPo.setPassword(MD5Utils.encrypt(password));
        userPo.setUpdateTime(new Date());
        return userMapper.update(userPo, userUpdateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserInfoById(UserInfoDO userinfo) {
        LambdaUpdateWrapper<UserInfoDO> userInfoUpdateWrapper = new LambdaUpdateWrapper<>();
        userInfoUpdateWrapper.eq(UserInfoDO::getUserId, userinfo.getUserId());
        UserInfoDO userInfo = new UserInfoDO();
        userInfo.setNickname(userinfo.getNickname());
        userInfo.setGender(userinfo.getGender());
        userInfo.setUpdateTime(new Date());
        userInfo.setEmail(userinfo.getEmail());
        return userInfoMapper.update(userInfo, userInfoUpdateWrapper);
    }

    @Override
    public String sendEmailCode(String email, String username) {
        // 发送验证码
        String smsCode = RandomUtil.randomNumbers(6);
        EmailHelper.sendSimple(email, "肉包仔", "验证码: " + smsCode);
        RedisHelper.set(RedisKey.PREFIX_USER_SMS_CODE + username, smsCode, 60, TimeUnit.SECONDS);
        return smsCode;
    }

    @Override
    public String getEmailCode(String username) {
        return RedisHelper.get(RedisKey.PREFIX_USER_SMS_CODE + username, String.class);
    }
}
