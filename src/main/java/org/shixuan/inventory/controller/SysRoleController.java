package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.SysRole;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理API
 */
@RestController
@RequestMapping("/roles")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 分页查询角色列表
     */
    @PreAuthorize("hasAuthority('sys:role')")
    @GetMapping("/page")
    public Result<PageResult<SysRole>> queryRoles(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String roleName) {
        PageResult<SysRole> pageResult = sysRoleService.queryRoles(pageNum, pageSize, roleName);
        return Result.success(pageResult);
    }
    
    /**
     * 获取所有角色列表（用于下拉框）
     */
    @PreAuthorize("hasAuthority('sys:role')")
    @GetMapping("/list")
    public Result<List<SysRole>> listAllRoles() {
        List<SysRole> roles = sysRoleService.listAllRoles();
        return Result.success(roles);
    }

    /**
     * 获取角色详情
     */
    @PreAuthorize("hasAuthority('sys:role')")
    @GetMapping("/{id}")
    public Result<SysRole> getRoleById(@PathVariable Long id) {
        SysRole role = sysRoleService.getRoleById(id);
        return Result.success(role);
    }

    /**
     * 创建角色
     */
    @PreAuthorize("hasAuthority('sys:role')")
    @PostMapping
    public Result<SysRole> createRole(@RequestBody SysRole sysRole) {
        SysRole createdRole = sysRoleService.createRole(sysRole);
        return Result.success(createdRole);
    }

    /**
     * 更新角色
     */
    @PreAuthorize("hasAuthority('sys:role')")
    @PutMapping("/{id}")
    public Result<SysRole> updateRole(@PathVariable Long id, @RequestBody SysRole sysRole) {
        sysRole.setId(id);
        SysRole updatedRole = sysRoleService.updateRole(sysRole);
        return Result.success(updatedRole);
    }

    /**
     * 删除角色
     */
    @PreAuthorize("hasAuthority('sys:role')")
    @DeleteMapping("/{id}")
    public Result<Void> deleteRole(@PathVariable Long id) {
        sysRoleService.deleteRole(id);
        return Result.success();
    }

    /**
     * 为角色分配权限
     */
    @PreAuthorize("hasAuthority('sys:role:assign_permissions')")
    @PostMapping("/{id}/permissions")
    public Result<Void> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        sysRoleService.assignPermissions(id, permissionIds);
        return Result.success();
    }
} 