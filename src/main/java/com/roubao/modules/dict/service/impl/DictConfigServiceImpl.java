package com.roubao.modules.dict.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.common.response.PageList;
import com.roubao.config.exception.ParameterCheckException;
import com.roubao.domain.DictConfigPO;
import com.roubao.modules.dict.dto.DictConfigSaveReqDto;
import com.roubao.modules.dict.dto.DictConfigRemoveReqDto;
import com.roubao.modules.dict.dto.DictConfigPageQueryReqDto;
import com.roubao.modules.dict.dto.DictConfigUpdateReqDto;
import com.roubao.modules.dict.dto.DictPairReqDto;
import com.roubao.modules.dict.mapper.DictConfigMapper;
import com.roubao.modules.dict.service.DictConfigService;
import com.roubao.util.EitherUtils;
import com.roubao.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<DictConfigPO> listDictConfigByKey(String key) {
        LambdaQueryWrapper<DictConfigPO> dictConfigQueryWrapper = new LambdaQueryWrapper<>();
        dictConfigQueryWrapper.eq(DictConfigPO::getDictKey, key);
        return dictConfigMapper.selectList(dictConfigQueryWrapper);
    }

    @Override
    public PageList<DictConfigPO> listPage(DictConfigPageQueryReqDto reqDto) {
        return PageUtils.doStart(reqDto, () -> dictConfigMapper.listPageDictConfig(reqDto), DictConfigPO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDictConfig(DictConfigSaveReqDto reqDto) {
        List<DictConfigPO> exitsUniques = dictConfigMapper.listDictConfigByUniques(reqDto);
        EitherUtils.boolE(CollUtil.isNotEmpty(exitsUniques)).either(() -> {
            throw new ParameterCheckException(String.format("已存在相同的字典配置: [%s - %s]", exitsUniques.get(0).getLabel(), exitsUniques.get(0).getValue()));
        }, () -> {
            // 数据正常就几条无需批量执行
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
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictConfig(DictConfigUpdateReqDto reqDto) {
        LambdaQueryWrapper<DictConfigPO> dictConfigQueryWrapper = new LambdaQueryWrapper<>();
        dictConfigQueryWrapper.eq(DictConfigPO::getDictKey, reqDto.getDictKey());
        dictConfigQueryWrapper.eq(DictConfigPO::getLabel, reqDto.getLabel());
        dictConfigQueryWrapper.eq(DictConfigPO::getValue, reqDto.getValue());
        boolean exists = dictConfigMapper.exists(dictConfigQueryWrapper);
        EitherUtils.boolE(exists).either(() -> {
            throw new RuntimeException("配置已存在");
        }, () -> {
            DictConfigPO po = new DictConfigPO();
            po.setId(reqDto.getId());
            po.setDictKey(reqDto.getDictKey());
            po.setLabel(reqDto.getLabel());
            po.setValue(reqDto.getValue());
            po.setUpdateTime(new Date());
            dictConfigMapper.updateById(po);
        });
    }

    @Override
    public void removeDictConfig(DictConfigRemoveReqDto reqDto) {
        dictConfigMapper.deleteById(reqDto.getId());
    }
}
