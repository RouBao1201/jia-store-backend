package com.roubao.modules.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.roubao.domian.AuthorityPO;
import com.roubao.domian.RolePO;
import com.roubao.domian.UserInfoPO;
import com.roubao.domian.UserPO;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.UserAuthorityDto;
import com.roubao.modules.user.dto.UserInfoDto;
import com.roubao.modules.user.dto.UserRoleDto;
import com.roubao.modules.user.mapper.UserInfoMapper;
import com.roubao.modules.user.mapper.UserMapper;
import com.roubao.modules.user.service.UserCacheService;
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
public class UserCacheServiceImpl implements UserCacheService {
    private static final String CURRENT_USER_CACHE_PREFIX = "CURRENT_USER_CACHE_";

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    @Cacheable(key = "'" + CURRENT_USER_CACHE_PREFIX + "'" + "+ #userId", unless = "#result == null")
    public CurrentUserDto getUserById(Integer userId) {
        // TODO 取值逻辑以及SQL后续优化调整
        UserPO user = userMapper.selectById(userId);
        if (user == null) {
            return null;
        }
        CurrentUserDto currentUserDto = new CurrentUserDto();
        // 用户信息
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userId);
        userInfoDto.setStatus(user.getStatus());
        userInfoDto.setUsername(user.getUserName());
        userInfoDto.setSuperAdmin(userMapper.isSuperAdmin(userId) != null);
        UserInfoPO userInfo = userInfoMapper.selectById(userId);
        if (userInfo != null) {
            userInfoDto.setGender(userInfo.getGender());
            userInfoDto.setAvatar(userInfo.getAvatar());
            userInfoDto.setNickname(userInfo.getNickname());
            userInfoDto.setEmail(userInfo.getEmail());
        }
        currentUserDto.setUserInfo(userInfoDto);

        // 用户权限
        List<AuthorityPO> authorityList = userMapper.queryUserAuthority(userId);
        List<UserAuthorityDto> userAuthList = new ArrayList<>();
        if (CollUtil.isNotEmpty(authorityList)) {
            authorityList.forEach(it -> {
                UserAuthorityDto dto = new UserAuthorityDto();
                BeanUtil.copyProperties(it, dto);
                userAuthList.add(dto);
            });
        }
        currentUserDto.setUserAuth(userAuthList);

        // 用户角色
        List<RolePO> roleList = userMapper.queryUserRole(userId);
        List<UserRoleDto> userRoleList = new ArrayList<>();
        if (CollUtil.isNotEmpty(roleList)) {
            roleList.forEach(it -> {
                UserRoleDto dto = new UserRoleDto();
                BeanUtil.copyProperties(it, dto);
                userRoleList.add(dto);
            });
        }
        currentUserDto.setUserRole(userRoleList);
        return currentUserDto;
    }

    @Override
    @CacheEvict(key = "'" + CURRENT_USER_CACHE_PREFIX + "'" + "+ #userId")
    public void invalidateUserCache(Integer userId) {
        log.info("清除用户缓存(下线):{}", userId);
    }
}
