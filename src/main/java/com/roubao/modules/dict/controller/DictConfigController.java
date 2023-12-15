package com.roubao.modules.dict.controller;

import com.roubao.common.response.RespResult;
import com.roubao.domian.DictConfigPO;
import com.roubao.modules.dict.dto.DictConfigReqDto;
import com.roubao.modules.dict.service.DictConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典配置API
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Tag(name = "字典配置API", description = "字典配置API")
@RestController
@RequestMapping("/dict")
public class DictConfigController {

    @Autowired
    private DictConfigService dictConfigService;

    @Operation(summary = "字典配置", description = "字典配置")
    @PostMapping("/dictConfig")
    public RespResult<List<DictConfigPO>> getDictConfig(@Validated @RequestBody DictConfigReqDto reqDto) {
        return RespResult.success(dictConfigService.getDictConfigByKey(reqDto.getDictKey()));
    }
}
