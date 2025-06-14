package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.SysUser;
import org.shixuan.inventory.dto.LoginRequest;
import org.shixuan.inventory.dto.LoginResponse;

/**
 * 用户服务接口
 */
public interface SysUserService {
    
    /**
     * 用户登录
     * 
     * @param loginRequest 登录请求
     * @return 登录响应结果
     */
    LoginResponse login(LoginRequest loginRequest);
    
    /**
     * 通过用户名查询用户
     * 
     * @param username 用户名
     * @return 用户对象
     */
    SysUser getUserByUsername(String username);
    
    /**
     * 通过用户ID查询用户
     * 
     * @param id 用户ID
     * @return 用户对象
     */
    SysUser getUserById(Long id);
    
    /**
     * 修改密码
     * 
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean updatePassword(Long userId, String oldPassword, String newPassword);
} 