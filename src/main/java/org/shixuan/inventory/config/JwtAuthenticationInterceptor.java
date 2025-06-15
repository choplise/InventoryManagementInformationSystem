package org.shixuan.inventory.config;

import cn.hutool.core.util.StrUtil;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT认证拦截器
 */
@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationInterceptor.class);

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        // 检查是否有免认证注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(IgnoreAuth.class) != null) {
            return true;
        }
        
        // 获取请求头中的token
        String authHeader = request.getHeader(tokenHeader);
        if (StrUtil.isBlank(authHeader)) {
            throw new ServiceException("请先登录");
        }
        
        // 从token中获取用户信息
        String token = jwtTokenUtil.getTokenFromFull(authHeader);
        if (token == null) {
            throw new ServiceException("token格式错误");
        }
        
        // 验证token是否有效
        String username = jwtTokenUtil.getUserNameFromToken(token);
        if (username == null) {
            throw new ServiceException("token已过期或无效");
        }
        
        // 获取用户ID和角色ID，并设置到请求属性中，便于后续使用
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        Long roleId = jwtTokenUtil.getRoleIdFromToken(token);
        request.setAttribute("userId", userId);
        request.setAttribute("roleId", roleId);
        request.setAttribute("username", username);
        
        LOGGER.info("认证通过，用户：{}，ID：{}", username, userId);
        LOGGER.debug("请求路径：{}，方法：{}", request.getRequestURI(), request.getMethod());
        return true;
    }
} 