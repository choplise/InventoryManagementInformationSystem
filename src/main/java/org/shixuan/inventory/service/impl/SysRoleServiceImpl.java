package org.shixuan.inventory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.SysRole;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.SysRoleMapper;
import org.shixuan.inventory.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageResult<SysRole> queryRoles(int pageNum, int pageSize, String roleName) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> list = sysRoleMapper.selectList(roleName);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    @Override
    public List<SysRole> listAllRoles() {
        return sysRoleMapper.selectAll();
    }

    @Override
    public SysRole getRoleById(Long id) {
        SysRole role = sysRoleMapper.selectById(id);
        if (role != null) {
            List<Long> permissionIds = sysRoleMapper.selectPermissionIdsByRoleId(id);
            role.setPermissionIds(permissionIds);
        }
        return role;
    }

    @Override
    public SysRole createRole(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
        return sysRole;
    }

    @Override
    public SysRole updateRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
        return sysRole;
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        // TODO: 检查该角色是否已分配给用户
        sysRoleMapper.deleteRolePermissionsByRoleId(id);
        sysRoleMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        // 1. 删除该角色之前的所有权限
        sysRoleMapper.deleteRolePermissionsByRoleId(roleId);
        // 2. 如果权限列表不为空，则插入新的权限
        if (!CollectionUtils.isEmpty(permissionIds)) {
            sysRoleMapper.batchInsertRolePermissions(roleId, permissionIds);
        }
    }
} 