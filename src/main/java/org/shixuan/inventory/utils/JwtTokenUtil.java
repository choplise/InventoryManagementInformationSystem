package org.shixuan.inventory.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT token工具类
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_ROLE_ID = "roleId";
    
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 根据用户信息生成token
     */
    public String generateToken(String username, Long userId, Long roleId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_USER_ID, userId);
        claims.put(CLAIM_KEY_ROLE_ID, roleId);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    
    /**
     * 从token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        Long userId;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = Long.parseLong(claims.get(CLAIM_KEY_USER_ID).toString());
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }
    
    /**
     * 从token中获取角色ID
     */
    public Long getRoleIdFromToken(String token) {
        Long roleId;
        try {
            Claims claims = getClaimsFromToken(token);
            roleId = Long.parseLong(claims.get(CLAIM_KEY_ROLE_ID).toString());
        } catch (Exception e) {
            roleId = null;
        }
        return roleId;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String usernameFromToken = getUserNameFromToken(token);
        return usernameFromToken != null && usernameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 验证token是否还有效
     *
     * @param token    客户端传入的token
     * @param username 从数据库中查询出来的用户名
     */
    public boolean validateToken(String token, String username) {
        String usernameFromToken = getUserNameFromToken(token);
        return usernameFromToken != null && usernameFromToken.equals(username) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.error("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 根据负载生成JWT的token
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    /**
     * 获取完整的token
     */
    public String getFullToken(String token) {
        return tokenHead + " " + token;
    }
    
    /**
     * 从完整token中截取token
     */
    public String getTokenFromFull(String fullToken) {
        if (fullToken != null && fullToken.startsWith(tokenHead)) {
            return fullToken.substring(tokenHead.length()).trim();
        }
        return null;
    }

    public String getUsernameFromToken(String authToken) {
        String username;
        try {
            Claims claims = getClaimsFromToken(authToken);
            username = claims.get(CLAIM_KEY_USERNAME, String.class);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
}