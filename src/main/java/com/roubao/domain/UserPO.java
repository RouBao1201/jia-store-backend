package com.roubao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/9
 **/
@Data
@ToString
@TableName(value = "user")
public class UserPO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6814434220139328820L;

    // 禁用状态
    public static final Integer STATUS_DISABLE = 0;
    // 启用状态
    public static final Integer STATUS_ENABLED = 1;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    @TableField("userName")
    private String userName;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 状态
     */
    @TableField("status")
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
