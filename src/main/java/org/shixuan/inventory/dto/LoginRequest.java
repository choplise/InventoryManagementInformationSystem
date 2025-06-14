package org.shixuan.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录请求参数
 */
@Setter
@Getter
public class LoginRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}