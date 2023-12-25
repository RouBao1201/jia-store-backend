package com.roubao.modules.dict.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 字典配置新增请求体
 *
 * @author: SongYanBin
 * @date: 2023-12-18
 */
@Schema(name = "字典配置新增请求体", description = "字典配置新增请求体")
@Data
@ToString
public class DictConfigSaveRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = 2756055294993789242L;

    @Schema(name = "dictKey", description = "字典KEY")
    @NotBlank(message = "字典KEY不可为空")
    private String dictKey;

    @Schema(name = "dictPair", description = "键值对集合")
    @Valid
    @NotNull(message = "键值对不可为空")
    List<DictPairRequest> dictPair;
}
