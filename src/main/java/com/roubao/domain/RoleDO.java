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
 * 角色表
 *
 * @author: SongYanBin
 * @date: 2023-12-11
 */
@Data
@ToString
@TableName(value = "role")
public class RoleDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4058147078997613255L;

    // 禁用状态
    public static final Integer STATUS_DISABLE = 0;
    // 启用状态
    public static final Integer STATUS_ENABLED = 1;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

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
