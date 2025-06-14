package org.shixuan.inventory.config;

import org.shixuan.inventory.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器，用于在每次请求中校验Token
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        LOGGER.info("处理请求: {}", requestPath);
        
        String authHeader = request.getHeader("Authorization");
        String authToken = null;
        String username = null;
        Long userId = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authToken = authHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                userId = jwtTokenUtil.getUserIdFromToken(authToken);
            } catch (Exception e) {
                LOGGER.warn("无法从Token中获取用户信息或Token已过期", e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            boolean isValid = jwtTokenUtil.validateToken(authToken, userDetails);

            if (isValid) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        // 设置请求属性，确保所有值都被正确设置
        request.setAttribute("username", username);
        request.setAttribute("authToken", authToken);
        request.setAttribute("userId", userId);
        
        LOGGER.info("请求属性设置: username={}, userId={}", username, userId);
        
        filterChain.doFilter(request, response);
    }
} 