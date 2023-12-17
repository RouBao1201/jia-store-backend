package com.roubao.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 字典表
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Data
@ToString
@TableName(value = "dict_config")
public class DictConfigPO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4242699905342207660L;

    /**
     * 字典KEY
     */
    @TableField("dict_key")
    private String dictKey;

    /**
     * 标签
     */
    @TableField("label")
    private String label;

    /**
     * 值
     */
    @TableField("value")
    private String value;

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
