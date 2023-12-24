package com.roubao.modules.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roubao.common.response.PageList;
import com.roubao.domain.PermissionPO;
import com.roubao.domain.RolePO;
import com.roubao.modules.role.dto.RoleChangedStatusReqDto;
import com.roubao.modules.role.dto.RolePageQueryReqDto;
import com.roubao.modules.role.dto.RoleSaveReqDto;
import com.roubao.modules.role.mapper.RoleMapper;
import com.roubao.modules.role.service.RoleService;
import com.roubao.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageList<RolePO> listPage(RolePageQueryReqDto reqDto) {
        return PageUtils.doStart(reqDto, () -> roleMapper.listPage(reqDto), RolePO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changedStatus(RoleChangedStatusReqDto reqDto) {
        RolePO po = new RolePO();
        po.setId(reqDto.getId());
        po.setStatus(reqDto.getStatus());
        roleMapper.updateById(po);
    }

    @Override
    public void saveRole(RoleSaveReqDto reqDto) {
        RolePO po = new RolePO();
        po.setName(reqDto.getName());
        po.setStatus(reqDto.getStatus());
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        roleMapper.insert(po);
    }

    @Override
    public List<RolePO> listAllRole() {
        LambdaQueryWrapper<RolePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePO::getStatus, RolePO.STATUS_ENABLED);
        return roleMapper.selectList(wrapper);
    }

    @Override
    public List<PermissionPO> listRolePermission(Integer roleId) {
        return roleMapper.listRolePermission(roleId);
    }
}
