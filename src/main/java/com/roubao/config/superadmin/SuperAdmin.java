package com.roubao.config.superadmin;

import lombok.Data;
import lombok.ToString;

/**
 * 超级管理员bean
 *
 * @author: SongYanBin
 * @date: 2023-12-12
 */
@Data
@ToString
public class SuperAdmin {
    public static final String BEAN_NAME = "superAdmin";

    private Integer userId;

    public SuperAdmin(Integer userId) {
        this.userId = userId;
    }
}
