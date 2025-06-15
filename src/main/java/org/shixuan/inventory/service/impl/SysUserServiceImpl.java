package org.shixuan.inventory.service.impl;

import org.shixuan.inventory.domain.SysRole;
import org.shixuan.inventory.domain.SysUser;
import org.shixuan.inventory.dto.LoginRequest;
import org.shixuan.inventory.dto.LoginResponse;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.SysRoleMapper;
import org.shixuan.inventory.mapper.SysUserMapper;
import org.shixuan.inventory.service.SysUserService;
import org.shixuan.inventory.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);
    
    @Autowired
    private SysUserMapper userMapper;
    
    @Autowired
    private SysRoleMapper roleMapper;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 查询用户
        SysUser user = userMapper.selectByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        
        // 检查用户状态
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new ServiceException("账号已被禁用");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        
        // 查询角色
        SysRole role = roleMapper.selectById(user.getRoleId());
        if (role == null) {
            throw new ServiceException("用户角色不存在");
        }
        
        // 生成token
        String token = jwtTokenUtil.generateToken(user.getUsername(), user.getId(), user.getRoleId());
        
        // 构建登录响应
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                user.getId(), 
                user.getUsername(), 
                user.getRealName(), 
                role.getRoleName());
        
        return new LoginResponse(token, userInfo);
    }
    
    @Override
    public SysUser getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    @Override
    public SysUser getUserById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        // 查询用户
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("原密码错误");
        }
        
        // 加密新密码
        String encodedPassword = passwordEncoder.encode(newPassword);
        
        // 更新密码
        int rows = userMapper.updatePassword(userId, encodedPassword);
        return rows > 0;
    }
} 