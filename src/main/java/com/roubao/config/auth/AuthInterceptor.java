package com.roubao.config.auth;

import cn.hutool.core.util.ObjUtil;
import com.roubao.common.constants.RedisKey;
import com.roubao.config.cache.token.TokenCacheHolder;
import com.roubao.config.exception.AuthException;
import com.roubao.config.superadmin.SuperAdmin;
import com.roubao.helper.RedisHelper;
import com.roubao.util.SpringContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;


/**
 * 自定义拦截器（权限拦截器）
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/4/4
 **/
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 校验超级管理员是否上线（无敌的存在）
            if (this.checkSuperAdminOnline()) {
                return true;
            }
            // token校验
            this.checkToken(request, (HandlerMethod) handler);
            // 校验用户权限
            this.checkApiAccess((HandlerMethod) handler);
        }
        return true;
    }

    /**
     * 校验超级管理员是否上线
     */
    private boolean checkSuperAdminOnline() {
        // 超级管理员是无敌的存在
        SuperAdmin superAdminBean = SpringContextHolder.getBean(SuperAdmin.class, true);
        return superAdminBean != null;
    }

    /**
     * token校验
     */
    private void checkToken(HttpServletRequest request, HandlerMethod handler) {
        // 有跳过Token校验注解则不校验
        DisableToken disableToken = handler.getMethod().getAnnotation(DisableToken.class);
        if (disableToken == null) {
            // 校验token合法性
            String token = TokenCacheHolder.getToken(request);
            if (token != null) {
                Integer userId = RedisHelper.get(RedisKey.PREFIX_USER_TOKEN + token, Integer.class);
                if (ObjUtil.isEmpty(userId)) {
                    throw new AuthException("用户登录失效，请重新登录");
                }
                TokenCacheHolder.renewalToken(token, 30, TimeUnit.MINUTES);
            } else {
                throw new AuthException("用户未登录授权，请先登录");
            }
        }
    }

    /**
     * 校验用户接口访问权限
     */
    private void checkApiAccess(HandlerMethod handler) {
        ApiAccess checkAccessAnno = handler.getMethod().getAnnotation(ApiAccess.class);
        if (checkAccessAnno != null) {
            // TODO 接口权限校验
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
