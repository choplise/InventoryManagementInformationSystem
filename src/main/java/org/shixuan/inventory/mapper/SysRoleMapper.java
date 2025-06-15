package org.shixuan.inventory.mapper;

import org.shixuan.inventory.domain.SysRole;

/**
 * 角色Mapper接口
 */
public interface SysRoleMapper {
    
    /**
     * 通过角色ID查询角色
     * 
     * @param id 角色ID
     * @return 角色对象
     */
    SysRole selectById(Long id);
    
    /**
     * 通过角色编码查询角色
     * 
     * @param roleCode 角色编码
     * @return 角色对象
     */
    SysRole selectByRoleCode(String roleCode);
} 