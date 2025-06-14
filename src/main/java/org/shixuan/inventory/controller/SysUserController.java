package org.shixuan.inventory.controller;

import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.CommonResult;
import org.shixuan.inventory.domain.SysUser;
import org.shixuan.inventory.service.SysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 Controller
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 分页查询用户列表
     */
    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping("/page")
    public CommonResult<PageInfo<SysUser>> listUsers(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<SysUser> pageInfo = sysUserService.listUsers(pageNum, pageSize);
        return CommonResult.success(pageInfo);
    }

    /**
     * 创建用户
     */
    @PreAuthorize("hasAuthority('sys:user')")
    @PostMapping
    public CommonResult<SysUser> createUser(@RequestBody SysUser user) {
        // 密码在service层加密
        SysUser createdUser = sysUserService.createUser(user);
        return CommonResult.success(createdUser);
    }

    /**
     * 更新用户（如分配角色）
     */
    @PreAuthorize("hasAuthority('sys:user')")
    @PutMapping("/{id}")
    public CommonResult<SysUser> updateUser(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        SysUser updatedUser = sysUserService.updateUser(user);
        return CommonResult.success(updatedUser);
    }

    /**
     * 删除用户
     */
    @PreAuthorize("hasAuthority('sys:user')")
    @DeleteMapping("/{id}")
    public CommonResult<Void> deleteUser(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return CommonResult.success(null);
    }
} 