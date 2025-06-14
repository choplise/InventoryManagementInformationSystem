package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.SysRole;

import java.util.List;

/**
 * 角色Mapper接口
 */
public interface SysRoleMapper {

    /**
     * 分页查询角色列表
     */
    List<SysRole> selectList(@Param("roleName") String roleName);

    /**
     * 获取所有角色
     */
    List<SysRole> selectAll();

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

    /**
     * 新增角色
     */
    int insert(SysRole sysRole);

    /**
     * 修改角色
     */
    int update(SysRole sysRole);

    /**
     * 删除角色
     */
    int deleteById(Long id);

    /**
     * 根据角色ID查询权限ID列表
     */
    List<Long> selectPermissionIdsByRoleId(Long roleId);

    /**
     * 根据角色ID删除角色与权限的关联关系
     */
    void deleteRolePermissionsByRoleId(Long roleId);

    /**
     * 批量插入角色与权限的关联关系
     */
    void batchInsertRolePermissions(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
} 