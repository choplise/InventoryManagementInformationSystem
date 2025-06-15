package org.shixuan.inventory.controller;

import org.shixuan.inventory.config.IgnoreAuth;
import org.shixuan.inventory.dto.LoginRequest;
import org.shixuan.inventory.dto.LoginResponse;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private SysUserService userService;
    
    /**
     * 登录
     */
    @PostMapping("/login")
    @IgnoreAuth
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return Result.success(loginResponse, "登录成功");
    }
    
    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(HttpServletRequest request) {
        // 从请求属性中获取用户信息，这些属性是由JwtAuthenticationInterceptor设置的
        Long userId = (Long) request.getAttribute("userId");
        String username = (String) request.getAttribute("username");
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("username", username);
        
        return Result.success(userInfo);
    }
    
    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    public Result<?> updatePassword(
            HttpServletRequest request,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        Long userId = (Long) request.getAttribute("userId");
        boolean success = userService.updatePassword(userId, oldPassword, newPassword);
        if (success) {
            return Result.success(null, "密码修改成功");
        } else {
            return Result.failed("密码修改失败");
        }
    }
    
    /**
     * 登出
     */
    @GetMapping("/logout")
    public Result<?> logout() {
        // JWT是无状态的，客户端只需要删除token即可，服务端不需要特殊处理
        return Result.success(null, "登出成功");
    }
} 