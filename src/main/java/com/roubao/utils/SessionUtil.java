package com.roubao.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Session工具类
 *
 * @author SongYanBin
 * @copyright ©2023-2099 SongYanBin. All rights reserved.
 * @since 2023/6/15
 **/
@Slf4j
public class SessionUtil {
    /**
     * 获取HttpSession
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        HttpServletRequest currentRequest = getRequest();
        if (currentRequest != null) {
            return currentRequest.getSession();
        }
        return null;
    }

    /**
     * 获取session数据
     *
     * @param key key
     * @return Object
     */
    public static Object getAttribute(String key) {
        HttpSession session = getSession();
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * 设置session数据
     *
     * @param key   key
     * @param value value
     */
    public static void setAttribute(String key, Object value) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    /**
     * 移除session数据
     *
     * @param key key
     */
    public static void removeAttribute(String key) {
        HttpSession session = getSession();
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * 获取当前应用名
     *
     * @return ContextPath
     */
    public static String getContextPath() {
        HttpServletRequest currentRequest = getRequest();
        if (currentRequest != null) {
            return currentRequest.getContextPath();
        }
        return null;
    }

    /**
     * 获取当前请求路径
     *
     * @return ServletPath
     */
    public static String getServletPath() {
        HttpServletRequest currentRequest = getRequest();
        if (currentRequest != null) {
            return currentRequest.getServletPath();
        }
        return null;
    }

    /**
     * 获取当前sessionId
     *
     * @return String
     */
    public static String getSessionId() {
        HttpSession session = getSession();
        if (session != null) {
            return session.getId();
        }
        return null;
    }

    /**
     * 获取当前HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.error("SessionUtil ==> RequestAttributes is null.");
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
