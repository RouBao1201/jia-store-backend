package com.roubao.domian;

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
 * 权限
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
@TableName("authority")
@Data
@ToString
public class AuthorityPO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8028989078975000753L;

    // 禁用状态
    public static final Integer STATUS_DISABLE = 0;
    // 启用状态
    public static final Integer STATUS_ENABLED = 1;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @TableField("id")
    private Integer id;

    /**
     * 权限KEY
     */
    @TableField("auth_key")
    private String authKey;

    /**
     * 权限名称
     */
    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

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
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
}
