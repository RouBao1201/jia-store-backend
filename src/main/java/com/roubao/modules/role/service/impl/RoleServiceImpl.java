package com.roubao.modules.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.roubao.common.response.PageList;
import com.roubao.domain.PermissionDO;
import com.roubao.domain.RoleDO;
import com.roubao.domain.RolePermissionDO;
import com.roubao.modules.role.mapper.RoleMapper;
import com.roubao.modules.role.mapper.RolePermissionMapper;
import com.roubao.modules.role.request.RoleChangedStatusRequest;
import com.roubao.modules.role.request.RolePageQueryRequest;
import com.roubao.modules.role.request.RolePermissionChangedRequest;
import com.roubao.modules.role.request.RoleSaveRequest;
import com.roubao.modules.role.service.RoleService;
import com.roubao.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 角色业务接口实现
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public PageList<RoleDO> listPage(RolePageQueryRequest request) {
        return PageUtils.doStart(request, () -> roleMapper.listPage(request), RoleDO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changedStatus(RoleChangedStatusRequest request) {
        RoleDO po = new RoleDO();
        po.setId(request.getId());
        po.setStatus(request.getStatus());
        roleMapper.updateById(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(RoleSaveRequest request) {
        RoleDO po = new RoleDO();
        po.setName(request.getName());
        po.setStatus(request.getStatus());
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        roleMapper.insert(po);
    }

    @Override
    public List<RoleDO> listAllRole() {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getStatus, RoleDO.STATUS_ENABLED);
        return roleMapper.selectList(wrapper);
    }

    @Override
    public List<PermissionDO> listRolePermissions(Integer roleId) {
        return roleMapper.listRolePermissions(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeRolePermissions(RolePermissionChangedRequest request) {
        if ("REMOVE".equals(request.getType())) {
            LambdaUpdateWrapper<RolePermissionDO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(RolePermissionDO::getRoleId, request.getRoleId());
            wrapper.in(RolePermissionDO::getPermissionId, request.getPermissionIds());
            rolePermissionMapper.delete(wrapper);
        } else {
            RolePermissionDO permission;
            for (Integer permissionId : request.getPermissionIds()) {
                permission = new RolePermissionDO();
                permission.setRoleId(request.getRoleId());
                permission.setPermissionId(permissionId);
                permission.setCreateTime(new Date());
                permission.setUpdateTime(new Date());
                rolePermissionMapper.insert(permission);
            }
        }
    }
}
