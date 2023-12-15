package com.roubao.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.domian.DictConfigPO;
import com.roubao.modules.dict.mapper.DictConfigMapper;
import com.roubao.modules.dict.service.DictConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
