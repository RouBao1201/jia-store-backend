package com.roubao.config.auth;

import com.roubao.config.exception.AuthException;
import com.roubao.config.token.TokenCacheHolder;
import com.roubao.modules.user.dto.CurrentUserDto;
import com.roubao.modules.user.service.UserService;
import com.roubao.utils.SpringContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 自定义拦截器（权限拦截器）
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/4/4
 **/
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 有跳过Token校验注解则不校验
            SkipCheckToken skipCheckTokenAnno = ((HandlerMethod) handler).getMethod().getAnnotation(SkipCheckToken.class);
            if (skipCheckTokenAnno == null) {
                // 校验token合法性
                String token = request.getHeader(TokenCacheHolder.TOKEN_HEADER_KEY);
                TokenCacheHolder tokenCacheHolder = SpringContextHolder.getBean(TokenCacheHolder.class);
                if (token == null || !tokenCacheHolder.verifyToken(token)) {
                    throw new AuthException("用户登录失效，请重新登录");
                }
            }

            // 校验用户权限
            CheckAccess checkAccessAnno = ((HandlerMethod) handler).getMethod().getAnnotation(CheckAccess.class);
            if (checkAccessAnno != null) {
                String[] value = checkAccessAnno.value();
                if (value.length == 0) {
                    return true;
                } else {
                    UserService userServiceBean = SpringContextHolder.getBean(UserService.class);
                    CurrentUserDto currentUser = userServiceBean.getCurrentUser();
                    // TODO 用户权限校验
                }
            }
        }
        return true;
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
