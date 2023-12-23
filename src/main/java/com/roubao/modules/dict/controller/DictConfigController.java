package com.roubao.modules.dict.controller;

import cn.hutool.core.lang.Assert;
import com.roubao.common.response.PageResult;
import com.roubao.common.response.RespResult;
import com.roubao.domain.DictConfigPO;
import com.roubao.modules.dict.dto.DictConfigPageQueryReqDto;
import com.roubao.modules.dict.dto.DictConfigRemoveReqDto;
import com.roubao.modules.dict.dto.DictConfigReqDto;
import com.roubao.modules.dict.dto.DictConfigSaveReqDto;
import com.roubao.modules.dict.dto.DictConfigUpdateReqDto;
import com.roubao.modules.dict.service.DictConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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
    @PostMapping("/listDictConfig")
    public RespResult<List<DictConfigPO>> listDictConfig(@Validated @RequestBody DictConfigReqDto reqDto) {
        return RespResult.success(dictConfigService.listDictConfigByKey(reqDto.getDictKey()));
    }

    @Operation(summary = "分页查询字典配置", description = "分页查询字典配置")
    @PostMapping("/listPage")
    public PageResult<DictConfigPO> listPage(@Validated @RequestBody DictConfigPageQueryReqDto reqDto) {
        return PageResult.success(dictConfigService.listPage(reqDto));
    }

    @Operation(summary = "新增字典配置", description = "新增字典配置")
    @PostMapping("/save")
    public RespResult<Objects> saveDictConfig(@Validated @RequestBody DictConfigSaveReqDto reqDto) {
        dictConfigService.saveDictConfig(reqDto);
        return RespResult.success("新增成功");
    }

    @Operation(summary = "修改字典配置", description = "新增字典配置")
    @PutMapping("/update")
    public RespResult<Objects> updateDictConfig(@Validated @RequestBody DictConfigUpdateReqDto reqDto) {
        dictConfigService.updateDictConfig(reqDto);
        return RespResult.success("修改成功");
    }

    @Operation(summary = "删除字典配置", description = "删除字典配置")
    @DeleteMapping("/remove")
    public RespResult<Objects> removeDictConfig(@Validated @RequestBody DictConfigRemoveReqDto reqDto) {
        dictConfigService.removeDictConfig(reqDto);
        return RespResult.success("删除成功");
    }
}
