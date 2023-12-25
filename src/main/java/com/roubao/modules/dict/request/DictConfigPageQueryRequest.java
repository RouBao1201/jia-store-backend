package com.roubao.modules.dict.request;

import com.roubao.common.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;

/**
 * 分页查询字典配置请求体
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/17
 **/
@Schema(name = "分页查询字典配置请求体", description = "分页查询字典配置请求体")
@Data
@ToString
public class DictConfigPageQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 3591913919198620435L;

    @Schema(name = "dictKey", description = "字典KEY")
    private String dictKey;

    @Schema(name = "label", description = "字典标签")
    private String label;

    @Schema(name = "value", description = "字典值")
    private String value;
}
