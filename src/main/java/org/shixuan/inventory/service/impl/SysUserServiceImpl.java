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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;

/**
 * 用户服务实现类
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);
    
    private final SysUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private SysRoleMapper roleMapper;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    public SysUserServiceImpl(SysUserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 查询用户
        SysUser user = userMapper.findByUsername(loginRequest.getUsername());
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
                user.getRoleId());
        
        return new LoginResponse(token, userInfo);
    }
    
    @Override
    public SysUser getUserById(Long id) {
        return userMapper.findById(id);
    }
    
    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        // 查询用户
        SysUser user = userMapper.findById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("原密码错误");
        }
        
        // 加密新密码
        String newEncodedPassword = passwordEncoder.encode(newPassword);
        
        // 更新密码
        int rows = userMapper.updatePassword(userId, newEncodedPassword);
        return rows > 0;
    }

    @Override
    public PageInfo<SysUser> listUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 查询时需要关联角色名称
        return new PageInfo<>(userMapper.listUsersWithRole());
    }

    @Override
    public SysUser findUserById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public SysUser createUser(SysUser user) {
        // 对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return user;
    }

    @Override
    public SysUser updateUser(SysUser user) {
        // 不允许通过此接口修改密码
        user.setPassword(null);
        userMapper.update(user);
        return findUserById(user.getId()); // 返回更新后的完整信息
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
} 