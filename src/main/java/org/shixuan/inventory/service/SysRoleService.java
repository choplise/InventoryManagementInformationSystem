package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.SysRole;
import org.shixuan.inventory.dto.PageResult;

import java.util.List;

/**
 * 角色管理 Service 接口
 */
public interface SysRoleService {

    /**
     * 分页查询角色列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param roleName 角色名称 (可选)
     * @return 分页结果
     */
    PageResult<SysRole> queryRoles(int pageNum, int pageSize, String roleName);

    /**
     * 获取所有角色列表（用于下拉框）
     * @return 角色列表
     */
    List<SysRole> listAllRoles();

    /**
     * 根据ID获取角色详情，包含权限ID列表
     * @param id 角色ID
     * @return 角色对象
     */
    SysRole getRoleById(Long id);

    /**
     * 创建新角色
     * @param sysRole 角色信息
     * @return 创建后的角色
     */
    SysRole createRole(SysRole sysRole);

    /**
     * 更新角色信息
     * @param sysRole 角色信息
     * @return 更新后的角色
     */
    SysRole updateRole(SysRole sysRole);

    /**
     * 删除角色
     * @param id 角色ID
     */
    void deleteRole(Long id);

    /**
     * 为角色分配权限
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     */
    void assignPermissions(Long roleId, List<Long> permissionIds);
} 