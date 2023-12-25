package com.roubao.modules.dict.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.common.response.PageList;
import com.roubao.config.exception.ParameterCheckException;
import com.roubao.domain.DictConfigDO;
import com.roubao.modules.dict.mapper.DictConfigMapper;
import com.roubao.modules.dict.request.DictConfigPageQueryRequest;
import com.roubao.modules.dict.request.DictConfigRemoveRequest;
import com.roubao.modules.dict.request.DictConfigSaveRequest;
import com.roubao.modules.dict.request.DictConfigUpdateRequest;
import com.roubao.modules.dict.request.DictPairRequest;
import com.roubao.modules.dict.service.DictConfigService;
import com.roubao.util.EitherUtils;
import com.roubao.util.PageUtils;
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
@Service
public class DictConfigServiceImpl implements DictConfigService {
    @Autowired
    private DictConfigMapper dictConfigMapper;

    @Override
    public List<DictConfigDO> listDictConfigByKey(String key) {
        LambdaQueryWrapper<DictConfigDO> dictConfigQueryWrapper = new LambdaQueryWrapper<>();
        dictConfigQueryWrapper.eq(DictConfigDO::getDictKey, key);
        return dictConfigMapper.selectList(dictConfigQueryWrapper);
    }

    @Override
    public PageList<DictConfigDO> listPage(DictConfigPageQueryRequest request) {
        return PageUtils.doStart(request, () -> dictConfigMapper.listPageDictConfig(request), DictConfigDO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDictConfig(DictConfigSaveRequest request) {
        List<DictConfigDO> exitsUniques = dictConfigMapper.listDictConfigByUniques(request);
        EitherUtils.boolE(CollUtil.isNotEmpty(exitsUniques)).either(() -> {
            throw new ParameterCheckException(String.format("已存在相同的字典配置: [%s - %s]", exitsUniques.get(0).getLabel(), exitsUniques.get(0).getValue()));
        }, () -> {
            // 数据正常就几条无需批量执行
            List<DictPairRequest> dictPair = request.getDictPair();
            for (DictPairRequest pair : dictPair) {
                DictConfigDO po = new DictConfigDO();
                po.setDictKey(request.getDictKey());
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
    public void updateDictConfig(DictConfigUpdateRequest request) {
        LambdaQueryWrapper<DictConfigDO> dictConfigQueryWrapper = new LambdaQueryWrapper<>();
        dictConfigQueryWrapper.eq(DictConfigDO::getDictKey, request.getDictKey());
        dictConfigQueryWrapper.eq(DictConfigDO::getLabel, request.getLabel());
        dictConfigQueryWrapper.eq(DictConfigDO::getValue, request.getValue());
        boolean exists = dictConfigMapper.exists(dictConfigQueryWrapper);
        EitherUtils.boolE(exists).either(() -> {
            throw new RuntimeException("配置已存在");
        }, () -> {
            DictConfigDO po = new DictConfigDO();
            po.setId(request.getId());
            po.setDictKey(request.getDictKey());
            po.setLabel(request.getLabel());
            po.setValue(request.getValue());
            po.setUpdateTime(new Date());
            dictConfigMapper.updateById(po);
        });
    }

    @Override
    public void removeDictConfig(DictConfigRemoveRequest request) {
        dictConfigMapper.deleteById(request.getId());
    }
}
