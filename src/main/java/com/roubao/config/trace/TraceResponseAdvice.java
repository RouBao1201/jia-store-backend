package com.roubao.config.trace;

import com.roubao.common.response.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@RestControllerAdvice
//@ConditionalOnBean(WebTraceFilter.class)
public class TraceResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return defaultListableBeanFactory.containsBean("webTraceFilter");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof RespResult) {
            if (((RespResult<?>) body).getBody().getTraceId() == null && MDCTraceUtils.getTraceId() != null) {
                ((RespResult<?>) body).getBody().setTraceId(MDCTraceUtils.getTraceId());
            }
        }
        return body;
    }
}