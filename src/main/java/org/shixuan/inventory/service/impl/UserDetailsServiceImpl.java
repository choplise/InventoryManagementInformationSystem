package org.shixuan.inventory.service.impl;

import org.shixuan.inventory.domain.SysUser;
import org.shixuan.inventory.mapper.SysPermissionMapper;
import org.shixuan.inventory.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private final SysUserMapper userMapper;

    @Autowired
    private final SysPermissionMapper permissionMapper;

    public UserDetailsServiceImpl(SysUserMapper userMapper, SysPermissionMapper permissionMapper) {
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("开始为用户 '{}' 加载权限...", username);
        SysUser sysUser = userMapper.findByUsername(username);
        if (sysUser == null) {
            LOGGER.error("加载权限失败：用户 '{}' 不存在。", username);
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        LOGGER.info("用户 '{}' 的角色ID是: {}", username, sysUser.getRoleId());

        List<String> permissionCodes = permissionMapper.findPermissionCodesByRoleIds(Collections.singletonList(sysUser.getRoleId()));
        LOGGER.info("为用户 '{}' (角色ID: {}) 找到了 {} 个权限: {}", username, sysUser.getRoleId(), permissionCodes.size(), permissionCodes);

        List<GrantedAuthority> authorities = permissionCodes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
    }
} 