package org.shixuan.inventory.controller;

import org.shixuan.inventory.config.IgnoreAuth;
import org.shixuan.inventory.domain.SysUser;
import org.shixuan.inventory.dto.LoginRequest;
import org.shixuan.inventory.dto.LoginResponse;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.service.SysUserService;
import org.shixuan.inventory.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @PostMapping("/login")
    @IgnoreAuth
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        // 使用 AuthenticationManager 进行用户认证
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // 将认证信息存入 SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取 UserDetails
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 根据用户名查询完整的用户信息，以获取userId和roleId
        final SysUser sysUser = sysUserService.findByUsername(userDetails.getUsername());

        // 生成 JWT
        final String token = jwtTokenUtil.generateToken(sysUser.getUsername(), sysUser.getId(), sysUser.getRoleId());

        // 创建 UserInfo 对象
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                sysUser.getId(),
                sysUser.getUsername(),
                sysUser.getRoleId()
        );

        userInfo.setRealName(sysUser.getRealName());


        // 2. 创建 LoginResponse 对象
        LoginResponse loginResponse = new LoginResponse(token, userInfo);

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
        // 如果需要realName和roleId，也应该在拦截器中设置并在此获取
        // String realName = (String) request.getAttribute("realName");
        // Long roleId = (Long) request.getAttribute("roleId");


        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("userId", userId);
        userInfoMap.put("username", username);
        // userInfoMap.put("realName", realName);
        // userInfoMap.put("roleId", roleId);


        return Result.success(userInfoMap);
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
        boolean success = sysUserService.updatePassword(userId, oldPassword, newPassword);
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