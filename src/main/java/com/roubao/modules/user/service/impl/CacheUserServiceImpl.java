package com.roubao.modules.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.roubao.domain.PermissionPO;
import com.roubao.domain.RolePO;
import com.roubao.domain.UserInfoPO;
import com.roubao.domain.UserPO;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.UserPermissionDto;
import com.roubao.modules.user.dto.UserInfoDto;
import com.roubao.modules.user.dto.UserRoleDto;
import com.roubao.modules.user.mapper.PermissionMapper;
import com.roubao.modules.user.mapper.UserInfoMapper;
import com.roubao.modules.user.mapper.UserMapper;
import com.roubao.modules.user.service.CacheUserService;
import com.roubao.util.EitherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户业务实现（带缓存）
 *
 * @author: SongYanBin
 * @date: 2023-12-11
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "user")
public class CacheUserServiceImpl implements CacheUserService {
    private static final String CURRENT_USER_CACHE_PREFIX = "user:currentUser:";

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Cacheable(key = "'" + CURRENT_USER_CACHE_PREFIX + "'" + "+ #userId", unless = "#result == null")
    public CurrentUserDto getUserById(Integer userId) {
        UserPO user = userMapper.selectById(userId);
        if (user == null) {
            return null;
        }
        CurrentUserDto currentUserDto = new CurrentUserDto();
        List<UserPermissionDto> userAuthList = new ArrayList<>();
        Integer superAdmin = userMapper.isSuperAdmin(userId);
        EitherUtils.boolE(superAdmin != null).either(() -> {
            currentUserDto.setSuperAdmin(true);
            // 超级管理员获取所有权限
            List<PermissionPO> permissionList = permissionMapper.listPermissionByStatus(null);
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
            List<PermissionPO> permissionList = userMapper.listUserPermission(userId);
            if (CollUtil.isNotEmpty(permissionList)) {
                permissionList.forEach(it -> {
                    UserPermissionDto dto = new UserPermissionDto();
                    BeanUtil.copyProperties(it, dto);
                    userAuthList.add(dto);
                });
            }
            // 用户角色
            List<RolePO> roleList = userMapper.listUserRole(userId);
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
        UserInfoPO userInfo = userInfoMapper.selectById(userId);
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
    @CacheEvict(key = "'" + CURRENT_USER_CACHE_PREFIX + "'" + "+ #userId")
    public void invalidateUserCache(Integer userId) {
        log.info("清除用户缓存(下线):{}", userId);
    }
}
