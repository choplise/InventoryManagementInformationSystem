package org.shixuan.inventory.service;

import com.github.pagehelper.PageInfo;
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
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    SysUser findByUsername(String username);
    
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

    /**
     * 分页查询用户列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 用户分页信息
     */
    PageInfo<SysUser> listUsers(int pageNum, int pageSize);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    SysUser findUserById(Long id);

    /**
     * 创建新用户
     * @param user 用户信息
     * @return 创建后的用户信息
     */
    SysUser createUser(SysUser user);

    /**
     * 更新用户信息（特别是角色）
     * @param user 用户信息
     * @return 更新后的用户信息
     */
    SysUser updateUser(SysUser user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);
} 