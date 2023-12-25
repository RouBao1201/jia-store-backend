package com.roubao.modules.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.domain.PermissionDO;
import com.roubao.modules.permission.mapper.PermissionsMapper;
import com.roubao.modules.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: SongYanBin
 * @date: 2023-12-25
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionsMapper permissionsMapper;

    @Override
    public List<PermissionDO> listAllPermissions() {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getStatus, PermissionDO.STATUS_ENABLED);
        return permissionsMapper.selectList(wrapper);
    }
}
