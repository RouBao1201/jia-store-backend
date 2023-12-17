package com.roubao.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询统一响应实体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/17
 **/
@Schema(name = "分页查询统一响应实体", description = "分页查询统一响应实体")
@Data
@ToString
public class PageList<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -1430731266993430007L;

    @Schema(name = "current", description = "当前页码")
    private Integer current;

    @Schema(name = "pageSize", description = "每页数量")
    private Integer pageSize;

    @Schema(name = "total", description = "总数量")
    private Long total;

    @Schema(name = "list", description = "数据集合")
    private List<T> list;
}
