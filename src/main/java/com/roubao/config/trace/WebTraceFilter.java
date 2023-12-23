package com.roubao.config.trace;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 链路过滤器
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public class WebTraceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = request.getHeader(MDCTraceUtils.TRACE_ID_HEADER);
            if (StrUtil.isEmpty(traceId)) {
                MDCTraceUtils.generateAndPut();
            } else {
                MDCTraceUtils.putTrace(traceId);
            }
            filterChain.doFilter(request, response);
        } finally {
            MDCTraceUtils.removeTrace();
        }
    }
}
