package com.roubao.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.common.constants.ErrorCode;
import com.roubao.common.response.PageList;
import com.roubao.domain.DictConfigPO;
import com.roubao.modules.dict.dto.DictConfigCreateReqDto;
import com.roubao.modules.dict.dto.DictConfigDeleteReqDto;
import com.roubao.modules.dict.dto.DictConfigPageQueryReqDto;
import com.roubao.modules.dict.dto.DictConfigUpdateReqDto;
import com.roubao.modules.dict.dto.DictPairReqDto;
import com.roubao.modules.dict.mapper.DictConfigMapper;
import com.roubao.modules.dict.service.DictConfigService;
import com.roubao.utils.EitherUtil;
import com.roubao.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 字典业务实现
 *
 * @author: SongYanBin
 * @date: 2023-12-14
 */
@Slf4j
@Service
public class DictConfigServiceImpl implements DictConfigService {
    @Autowired
    private DictConfigMapper dictConfigMapper;

    @Override
    public List<DictConfigPO> getDictConfigByKey(String key) {
        LambdaQueryWrapper<DictConfigPO> dictConfigQueryWrapper = new LambdaQueryWrapper<>();
        dictConfigQueryWrapper.eq(DictConfigPO::getDictKey, key);
        return dictConfigMapper.selectList(dictConfigQueryWrapper);
    }

    @Override
    public PageList<DictConfigPO> getPageList(DictConfigPageQueryReqDto reqDto) {
        return PageUtil.doStart(reqDto, () -> dictConfigMapper.selectPageDictConfig(reqDto), DictConfigPO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDictConfig(DictConfigCreateReqDto reqDto) {
//        LambdaQueryWrapper<DictConfigPO> dictConfigQueryWrapper = new LambdaQueryWrapper<>();
//        dictConfigQueryWrapper.eq(DictConfigPO::getDictKey, reqDto.getDictKey());
//        dictConfigQueryWrapper.eq(DictConfigPO::getLabel, reqDto.getLabel());
//        dictConfigQueryWrapper.eq(DictConfigPO::getValue, reqDto.getValue());
//        boolean exists = dictConfigMapper.exists(dictConfigQueryWrapper);
//        EitherUtil.throwIf(exists, ErrorCode.PARAM_ERROR, "配置已存在");
        List<DictPairReqDto> dictPair = reqDto.getDictPair();
        for (DictPairReqDto pair : dictPair) {
            DictConfigPO po = new DictConfigPO();
            po.setDictKey(reqDto.getDictKey());
            po.setLabel(pair.getLabel());
            po.setValue(pair.getValue());
            po.setCreateTime(new Date());
            po.setUpdateTime(new Date());
            dictConfigMapper.insert(po);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictConfig(DictConfigUpdateReqDto reqDto) {
        LambdaQueryWrapper<DictConfigPO> dictConfigQueryWrapper = new LambdaQueryWrapper<>();
        dictConfigQueryWrapper.eq(DictConfigPO::getDictKey, reqDto.getDictKey());
        dictConfigQueryWrapper.eq(DictConfigPO::getLabel, reqDto.getLabel());
        dictConfigQueryWrapper.eq(DictConfigPO::getValue, reqDto.getValue());
        boolean exists = dictConfigMapper.exists(dictConfigQueryWrapper);
        EitherUtil.throwIf(exists, ErrorCode.PARAM_ERROR, "配置已存在");
        DictConfigPO po = new DictConfigPO();
        po.setId(reqDto.getId());
        po.setDictKey(reqDto.getDictKey());
        po.setLabel(reqDto.getLabel());
        po.setValue(reqDto.getValue());
        po.setUpdateTime(new Date());
        dictConfigMapper.updateById(po);
    }

    @Override
    public void deleteDictConfig(DictConfigDeleteReqDto reqDto) {
        dictConfigMapper.deleteById(reqDto.getId());
    }
}
