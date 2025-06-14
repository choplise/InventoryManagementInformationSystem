package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.CommonResult;
import org.shixuan.inventory.domain.SysPermission;
import org.shixuan.inventory.service.SysPermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理 Controller
 */
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    /**
     * 以树形结构获取所有权限
     */
    @PreAuthorize("hasAuthority('sys:permission')")
    @GetMapping("/tree")
    public CommonResult<List<SysPermission>> listPermissionsAsTree() {
        List<SysPermission> permissionTree = sysPermissionService.listPermissionsAsTree();
        return CommonResult.success(permissionTree);
    }

    /**
     * 新增权限
     */
    @PreAuthorize("hasAuthority('sys:permission')")
    @PostMapping
    public CommonResult<SysPermission> createPermission(@RequestBody SysPermission permission) {
        SysPermission createdPermission = sysPermissionService.createPermission(permission);
        return CommonResult.success(createdPermission);
    }

    /**
     * 修改权限
     */
    @PreAuthorize("hasAuthority('sys:permission')")
    @PutMapping("/{id}")
    public CommonResult<SysPermission> updatePermission(@PathVariable Long id, @RequestBody SysPermission permission) {
        permission.setId(id);
        SysPermission updatedPermission = sysPermissionService.updatePermission(permission);
        return CommonResult.success(updatedPermission);
    }

    /**
     * 删除权限
     */
    @PreAuthorize("hasAuthority('sys:permission')")
    @DeleteMapping("/{id}")
    public CommonResult<Void> deletePermission(@PathVariable Long id) {
        sysPermissionService.deletePermission(id);
        return CommonResult.success(null);
    }
} 