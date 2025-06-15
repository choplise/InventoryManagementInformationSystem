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
        private String roleName;
        
        public UserInfo() {
        }
        
        public UserInfo(Long id, String username, String realName, String roleName) {
            this.id = id;
            this.username = username;
            this.realName = realName;
            this.roleName = roleName;
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getRealName() {
            return realName;
        }
        
        public void setRealName(String realName) {
            this.realName = realName;
        }
        
        public String getRoleName() {
            return roleName;
        }
        
        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }
} 