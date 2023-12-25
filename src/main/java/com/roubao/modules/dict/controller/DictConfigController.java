package com.roubao.modules.dict.controller;

import com.roubao.common.response.PageResult;
import com.roubao.common.response.RespResult;
import com.roubao.domain.DictConfigDO;
import com.roubao.modules.dict.request.DictConfigPageQueryRequest;
import com.roubao.modules.dict.request.DictConfigRemoveRequest;
import com.roubao.modules.dict.request.DictConfigRequest;
import com.roubao.modules.dict.request.DictConfigSaveRequest;
import com.roubao.modules.dict.request.DictConfigUpdateRequest;
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
    public RespResult<List<DictConfigDO>> listDictConfig(@Validated @RequestBody DictConfigRequest request) {
        return RespResult.success(dictConfigService.listDictConfigByKey(request.getDictKey()));
    }

    @Operation(summary = "分页查询字典配置", description = "分页查询字典配置")
    @PostMapping("/listPage")
    public PageResult<DictConfigDO> listPage(@Validated @RequestBody DictConfigPageQueryRequest request) {
        return PageResult.success(dictConfigService.listPage(request));
    }

    @Operation(summary = "新增字典配置", description = "新增字典配置")
    @PostMapping("/save")
    public RespResult<Objects> saveDictConfig(@Validated @RequestBody DictConfigSaveRequest request) {
        dictConfigService.saveDictConfig(request);
        return RespResult.success("新增成功");
    }

    @Operation(summary = "修改字典配置", description = "新增字典配置")
    @PutMapping("/update")
    public RespResult<Objects> updateDictConfig(@Validated @RequestBody DictConfigUpdateRequest request) {
        dictConfigService.updateDictConfig(request);
        return RespResult.success("修改成功");
    }

    @Operation(summary = "删除字典配置", description = "删除字典配置")
    @DeleteMapping("/remove")
    public RespResult<Objects> removeDictConfig(@Validated @RequestBody DictConfigRemoveRequest request) {
        dictConfigService.removeDictConfig(request);
        return RespResult.success("删除成功");
    }
}
