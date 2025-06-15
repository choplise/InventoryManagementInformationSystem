package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.SysUser;

/**
 * 用户Mapper接口
 */
public interface SysUserMapper {
    
    /**
     * 通过用户名查询用户
     * 
     * @param username 用户名
     * @return 用户对象
     */
    SysUser selectByUsername(String username);
    
    /**
     * 通过用户ID查询用户
     * 
     * @param id 用户ID
     * @return 用户对象
     */
    SysUser selectById(Long id);
    
    /**
     * 新增用户
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int insert(SysUser user);
    
    /**
     * 更新用户
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int update(SysUser user);
    
    /**
     * 更新用户密码
     * 
     * @param userId 用户ID
     * @param newPassword 新密码
     * @return 影响行数
     */
    int updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);
    
    /**
     * 通过用户ID删除用户
     * 
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Long id);
} 