package com.roubao.modules.dict.service;

import com.roubao.common.response.PageList;
import com.roubao.domain.DictConfigPO;
import com.roubao.modules.dict.dto.DictConfigSaveReqDto;
import com.roubao.modules.dict.dto.DictConfigRemoveReqDto;
import com.roubao.modules.dict.dto.DictConfigPageQueryReqDto;
import com.roubao.modules.dict.dto.DictConfigUpdateReqDto;

import java.util.List;

/**
 * 字典业务接口
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
public interface DictConfigService {

    /**
     * 获取字典配置
     *
     * @param key 字典KEY
     * @return 字典配置
     */
    List<DictConfigPO> listDictConfigByKey(String key);

    /**
     * 分页查询字典配置数据
     *
     * @param reqDto 请求体
     * @return 分页数据
     */
    PageList<DictConfigPO> listPage(DictConfigPageQueryReqDto reqDto);

    /**
     * 新增字典配置
     *
     * @param reqDto 请求体
     */
    void saveDictConfig(DictConfigSaveReqDto reqDto);

    /**
     * 修改字典配置
     *
     * @param reqDto 请求体
     */
    void updateDictConfig(DictConfigUpdateReqDto reqDto);

    /**
     * 删除字典配置
     *
     * @param reqDto 请求体
     */
    void removeDictConfig(DictConfigRemoveReqDto reqDto);
}
