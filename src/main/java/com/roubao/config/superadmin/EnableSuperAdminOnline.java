package com.roubao.config.superadmin;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用超级管理员在线模式（开发者模式）
 *
 * @author SongYanBin
 * @date 2020-04-20
 */
@Documented
@Target({
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Import(SuperAdminOnlineRegistrar.class)
public @interface EnableSuperAdminOnline {
    /**
     * 用户ID
     */
    int userId() default 0;

    /**
     * 指定环境启动
     */
    String[] includeProfiles() default {"dev", "test"};
}
