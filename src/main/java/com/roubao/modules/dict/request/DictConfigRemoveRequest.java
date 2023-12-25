package com.roubao.modules.dict.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典配置删除请求体
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
@Schema(name = "字典配置删除请求体", description = "字典配置删除请求体")
@Data
@ToString
public class DictConfigRemoveRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 845488841661194046L;

    @Schema(name = "id", description = "主键ID")
    @NotNull(message = "主键ID不可为空")
    private Integer id;
}
