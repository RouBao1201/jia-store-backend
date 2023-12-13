package com.roubao.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @author: SongYanBin
 * @date: 2023-12-13
 */
@Data
@ToString
@TableName(value = "user_info")
public class UserInfoPO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8662216618893386932L;

    @TableId(value = "user_id")
    @TableField("user_id")
    private Integer userId;
    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;
    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 性别
     */
    @TableField("gender")
    private Integer gender;
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
