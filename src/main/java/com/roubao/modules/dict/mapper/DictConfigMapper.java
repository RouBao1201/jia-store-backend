package com.roubao.modules.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roubao.domain.DictConfigPO;
import com.roubao.modules.dict.dto.DictConfigCreateReqDto;
import com.roubao.modules.dict.dto.DictConfigPageQueryReqDto;
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
public interface DictConfigMapper extends BaseMapper<DictConfigPO> {

    /**
     * 查询字典配置
     *
     * @param reqDto 查询请求体
     * @return 字典数据集合
     */
    List<DictConfigPO> selectPageDictConfig(DictConfigPageQueryReqDto reqDto);

    List<DictConfigPO> selectListByUniques(@Param("reqDto") DictConfigCreateReqDto reqDto);
}
