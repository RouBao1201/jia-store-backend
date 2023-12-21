package com.roubao.config.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口访问权限注解
 *
 * @author SongYanBin
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiAccess {
    /**
     * 权限KEY
     */
    String[] authKey() default {};

    /**
     * 权限类型
     */
    String authType() default "";
}
