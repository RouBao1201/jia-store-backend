package com.roubao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色表
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Data
@ToString
public class UserRoleDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5520060332159920407L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @TableField("id")
    private Integer id;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Integer roleId;

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
