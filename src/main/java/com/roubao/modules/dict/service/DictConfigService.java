package com.roubao.modules.dict.service;

import com.roubao.common.response.PageList;
import com.roubao.domain.DictConfigPO;
import com.roubao.modules.dict.dto.DictConfigCreateReqDto;
import com.roubao.modules.dict.dto.DictConfigDeleteReqDto;
import com.roubao.modules.dict.dto.DictConfigPageQueryReqDto;
import com.roubao.modules.dict.dto.DictConfigUpdateReqDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

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

    /**
     * 分页查询字典配置数据
     *
     * @param reqDto 请求体
     * @return 分页数据
     */
    PageList<DictConfigPO> getPageList(DictConfigPageQueryReqDto reqDto);

    /**
     * 新增字典配置
     *
     * @param reqDto 请求体
     */
    void createDictConfig(DictConfigCreateReqDto reqDto);

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
    void deleteDictConfig(DictConfigDeleteReqDto reqDto);
}
