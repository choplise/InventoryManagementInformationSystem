package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.SysPermission;

import java.util.List;

/**
 * 权限管理 Service 接口
 */
public interface SysPermissionService {

    /**
     * 获取所有权限，并以树形结构返回
     * @return 权限树
     */
    List<SysPermission> listPermissionsAsTree();

    /**
     * 新增权限
     * @param permission 权限信息
     * @return 新增的权限
     */
    SysPermission createPermission(SysPermission permission);

    /**
     * 修改权限
     * @param permission 权限信息
     * @return 修改后的权限
     */
    SysPermission updatePermission(SysPermission permission);

    /**
     * 删除权限
     * @param id 权限ID
     */
    void deletePermission(Long id);
} 