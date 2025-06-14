package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.SysPermission;

import java.util.List;

public interface SysPermissionMapper {

    /**
     * 根据角色ID列表查询权限编码
     * @param roleIds 角色ID列表
     * @return 权限编码列表
     */
    List<String> findPermissionCodesByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 查询所有权限
     * @return 权限列表
     */
    List<SysPermission> findAll();

    /**
     * 新增权限
     * @param permission 权限信息
     * @return 影响行数
     */
    int insert(SysPermission permission);

    /**
     * 修改权限
     * @param permission 权限信息
     * @return 影响行数
     */
    int update(SysPermission permission);

    /**
     * 根据ID删除权限
     * @param id 权限ID
     * @return 影响行数
     */
    int deleteById(Long id);
} 