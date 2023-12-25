package com.roubao.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询基础实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/17
 **/
@Schema(name = "分页查询基础实体", description = "分页查询基础实体")
@Data
@ToString
public class PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -9064747065930831304L;

    @Schema(name = "current", description = "当前页码", example = "1")
    @NotNull(message = "当前页码不可为空")
    private Integer current;

    @Schema(name = "pageSize", description = "每页数量", example = "20")
    @NotNull(message = "每页数量不可为空")
    private Integer pageSize;

    @Schema(name = "fieldsOrderBy", description = "排序字段（格式：字段名 [ASC/DESC]）", example = "id ASC")
    private List<String> fieldsOrderBy;

    /**
     * 组装排序字段
     *
     * @return 排序字段
     */
    public String assembleOrderBySql() {
        if (fieldsOrderBy != null && !fieldsOrderBy.isEmpty()) {
            StringBuilder orderByStr = new StringBuilder();
            fieldsOrderBy.forEach(it -> {
                orderByStr.append(it).append(",");
            });
            return orderByStr.toString();
        }
        return "";
    }
}
