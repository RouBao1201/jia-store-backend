package com.roubao.modules.dict.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典配置修改请求体
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
@Schema(name = "字典配置修改请求体", description = "字典配置修改请求体")
@Data
@ToString
public class DictConfigUpdateReqDto implements Serializable {


    @Serial
    private static final long serialVersionUID = 5919532938642980398L;

    @Schema(name = "id", description = "主键ID")
    @NotNull(message = "主键ID不可为空")
    private Integer id;

    @Schema(name = "dictKey", description = "字典KEY")
    @NotBlank(message = "字典KEY不可为空")
    private String dictKey;

    @Schema(name = "label", description = "标签")
    @NotBlank(message = "字典标签不可为空")
    private String label;

    @Schema(name = "value", description = "标签值")
    @NotBlank(message = "标签值不可为空")
    private String value;
}
