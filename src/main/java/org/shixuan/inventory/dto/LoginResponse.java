package org.shixuan.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 登录成功返回数据
 */
@Setter
@Getter
public class LoginResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * JWT token
     */
    private String token;
    
    /**
     * 用户信息
     */
    private UserInfo userInfo;
    
    public LoginResponse() {
    }
    
    public LoginResponse(String token, UserInfo userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }


    /**
     * 用户信息
     */
    @Setter
    @Getter
    public static class UserInfo {
        /**
         * 用户ID
         */
        private Long id;
        
        /**
         * 用户名
         */
        private String username;
        
        /**
         * 真实姓名
         */
        private String realName;
        
        /**
         * 角色名称
         */
        private Long roleId;
        
        public UserInfo() {
        }
        
        public UserInfo(Long id, String username,  Long roleId) {
            this.id = id;
            this.username = username;
            this.roleId = roleId;
        }

    }
} 