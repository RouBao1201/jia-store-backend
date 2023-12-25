package com.roubao.modules.dict.service;

import com.roubao.common.response.PageList;
import com.roubao.domain.DictConfigDO;
import com.roubao.modules.dict.request.DictConfigSaveRequest;
import com.roubao.modules.dict.request.DictConfigRemoveRequest;
import com.roubao.modules.dict.request.DictConfigPageQueryRequest;
import com.roubao.modules.dict.request.DictConfigUpdateRequest;

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
    List<DictConfigDO> listDictConfigByKey(String key);

    /**
     * 分页查询字典配置数据
     *
     * @param request 请求体
     * @return 分页数据
     */
    PageList<DictConfigDO> listPage(DictConfigPageQueryRequest request);

    /**
     * 新增字典配置
     *
     * @param request 请求体
     */
    void saveDictConfig(DictConfigSaveRequest request);

    /**
     * 修改字典配置
     *
     * @param request 请求体
     */
    void updateDictConfig(DictConfigUpdateRequest request);

    /**
     * 删除字典配置
     *
     * @param request 请求体
     */
    void removeDictConfig(DictConfigRemoveRequest request);
}
