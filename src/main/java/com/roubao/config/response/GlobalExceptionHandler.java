package com.roubao.config.response;

import com.roubao.common.response.RespResult;
import com.roubao.config.exception.AuthException;
import com.roubao.config.exception.ParameterCheckException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/4/4
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数异常处理@Validated
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        log.error("GlobalExceptionHandler ==> MethodArgumentNotValidException: {}", objectError.getDefaultMessage());
        return RespResult.error(HttpStatus.BAD_REQUEST.value(), "参数异常: " + objectError.getDefaultMessage());
    }

    /**
     * 参数异常处理@Validated全局
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RespResult<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        return RespResult.error(HttpStatus.BAD_REQUEST.value(), "参数异常: " + ex.getMessage());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(AuthException.class)
    public RespResult<Object> authExceptionHandler(AuthException ex) {
        log.error("GlobalExceptionHandler ==> AuthExceptionError: {}", ex.getMessage(), ex);
        return RespResult.error(HttpStatus.FORBIDDEN.value(), "无权访问: " + ex.getMessage());
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(ParameterCheckException.class)
    public RespResult<Object> parameterCheckExceptionHandler(ParameterCheckException ex) {
        log.error("GlobalExceptionHandler ==> ParameterCheckException: {}", ex.getMessage(), ex);
        return RespResult.error(HttpStatus.BAD_REQUEST.value(), "参数异常: " + ex.getMessage());
    }

    /**
     * 其他异常处理
     */
    @ExceptionHandler(Exception.class)
    public RespResult<Object> exceptionHandler(Exception ex) {
        log.error("GlobalExceptionHandler ==> Exception: {}", ex.getMessage(), ex);
        return RespResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器繁忙: " + ex.getMessage());
    }
}
