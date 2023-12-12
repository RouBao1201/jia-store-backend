package com.roubao.modules.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.config.exception.AuthException;
import com.roubao.config.exception.ParameterCheckException;
import com.roubao.config.token.TokenCacheHolder;
import com.roubao.domian.AuthorityPO;
import com.roubao.domian.UserPO;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.dto.LoginReqDto;
import com.roubao.modules.user.dto.LoginRespDto;
import com.roubao.modules.user.dto.RegisterReqDto;
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

    @Override
    public LoginRespDto login(LoginReqDto reqDto) {
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        userQueryWrapper.eq(UserPO::getPassword, MD5Util.encrypt(reqDto.getPassword()));
        userQueryWrapper.eq(UserPO::getStatus, 1);
        UserPO userPo = userMapper.selectOne(userQueryWrapper);
        if (userPo == null) {
            throw new ParameterCheckException("用户名和密码不存在");
        }
        LoginRespDto loginRespDto = new LoginRespDto();
        String token = tokenCacheHolder.generateTokenAndPutAtom(userPo.getId());
        loginRespDto.setToken(token);
        SessionUtil.setAttribute(USER_LOGIN_STATE, userPo.getId());
        return loginRespDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer register(RegisterReqDto reqDto) {
        LambdaQueryWrapper<UserPO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserPO::getUserName, reqDto.getUsername());
        UserPO user = userMapper.selectOne(userQueryWrapper);
        if (user != null) {
            throw new ParameterCheckException("用户名已存在");
        }
        UserPO userPo = new UserPO();
        userPo.setUserName(reqDto.getUsername());
        userPo.setPassword(MD5Util.encrypt(reqDto.getPassword()));
        userPo.setStatus(UserPO.STATUS_ENABLED);
        userPo.setCreateTime(new Date());
        userPo.setUpdateTime(new Date());
        return userMapper.insert(userPo);
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
            throw new AuthException("用户登录状态已失效");
        }
        CurrentUserDto user = userCacheService.getUserById(Integer.valueOf(currentUserId));
        if (user == null) {
            throw new AuthException("用户不存在");
        }
        return user;
    }

    @Override
    public boolean isSuperAdmin(Integer userId) {
        Integer superAdmin = userMapper.isSuperAdmin(userId);
        return superAdmin != null;
    }

    @Override
    public Map<String, AuthorityPO> getUserAuthority(Integer userId) {
        List<AuthorityPO> authorityList = userMapper.queryUserAuthority(userId);
        if (CollectionUtil.isNotEmpty(authorityList)) {
            return authorityList.stream().collect(Collectors.toMap(AuthorityPO::getAuthKey, Function.identity()));
        }
        return new HashMap<>();
    }
}
