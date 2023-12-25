package com.roubao.modules.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.DictConfigDO;
import com.roubao.modules.dict.request.DictConfigSaveRequest;
import com.roubao.modules.dict.request.DictConfigPageQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表Mapper
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Mapper
public interface DictConfigMapper extends BaseMapper<DictConfigDO> {

    /**
     * 查询字典配置
     *
     * @param reqDto 查询请求体
     * @return 字典数据集合
     */
    List<DictConfigDO> listPageDictConfig(DictConfigPageQueryRequest request);

    List<DictConfigDO> listDictConfigByUniques(@Param("request") DictConfigSaveRequest request);
}
