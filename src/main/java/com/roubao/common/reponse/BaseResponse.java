package com.roubao.common.reponse;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础响应
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Schema(name = "基础响应", description = "基础响应")
public class BaseResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -3394781860127767877L;
}
