package com.roubao;

import com.roubao.config.superadmin.EnableSuperAdminOnline;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/8
 **/
//@EnableSuperAdminOnline
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.roubao.modules.**.mapper")
@SpringBootApplication
public class JiaStoreApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JiaStoreApplication.class);
        app.run(args);
    }

}
