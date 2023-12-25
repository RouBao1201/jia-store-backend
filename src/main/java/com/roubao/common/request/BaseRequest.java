package com.roubao.common.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础请求
 *
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Schema(name = "基础请求", description = "基础请求")
public class BaseRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5797837067166411371L;
}
