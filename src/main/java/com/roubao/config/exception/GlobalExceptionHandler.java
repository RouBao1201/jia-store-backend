package com.roubao.config.exception;

import com.roubao.common.response.RespResult;
import com.roubao.config.asserts.AssertException;
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
        return RespResult.error(HttpStatus.BAD_REQUEST.value(), objectError.getDefaultMessage());
    }

    /**
     * 参数异常处理@Validated全局
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RespResult<Object> handleConstraintViolationExceptionHandler(ConstraintViolationException ex) {
        return RespResult.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * 断言异常
     */
    @ExceptionHandler(AssertException.class)
    public RespResult<Object> assertExceptionHandler(AssertException ex) {
        log.error("GlobalExceptionHandler ==> AssertException: {}", ex.getMessage(), ex);
        return RespResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 基础运行异常处理
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public RespResult<Object> baseRuntimeExceptionHandler(BaseRuntimeException ex) {
        log.error("GlobalExceptionHandler ==> BaseRuntimeException: {}", ex.getMessage(), ex);
        return RespResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public RespResult<Object> runtimeExceptionHandler(RuntimeException ex) {
        log.error("GlobalExceptionHandler ==> RuntimeException: {}", ex.getMessage(), ex);
        return RespResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器繁忙: " + ex.getMessage());
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
