package com.roubao.modules.dict.service;

import com.roubao.domian.DictConfigPO;

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
    List<DictConfigPO> getDictConfigByKey(String key);
}
