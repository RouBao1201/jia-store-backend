package com.roubao.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

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
public class PageReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -9064747065930831304L;

    @Schema(name = "current", description = "当前页码", example = "1")
    @NotNull(message = "当前页码不可为空")
    private Integer current;

    @Schema(name = "pageSize", description = "每页数量", example = "20")
    @NotNull(message = "每页数量不可为空")
    private Integer pageSize;
}
