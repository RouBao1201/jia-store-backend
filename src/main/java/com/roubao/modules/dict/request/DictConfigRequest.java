package com.roubao.modules.dict.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典配置查询请求体
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Schema(name = "字典配置查询请求体", description = "字典配置查询请求体")
@Data
@ToString
public class DictConfigRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2551662132005852732L;

    @Schema(name = "dictKey", description = "字典KEY")
    @NotBlank(message = "字典KEY不能为空")
    private String dictKey;
}
