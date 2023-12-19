package com.roubao.modules.dict.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典键值对请求体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/19
 **/
@Schema(name = "字典键值对请求体", description = "字典键值对请求体")
@Data
@ToString
public class DictPairReqDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1499010125559482744L;

    @Schema(name = "label", description = "标签")
    @NotBlank(message = "标签不可为空")
    private String label;

    @Schema(name = "value", description = "标签值")
    @NotBlank(message = "标签值不可为空")
    private String value;
}
