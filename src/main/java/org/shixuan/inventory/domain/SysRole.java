package org.shixuan.inventory.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统角色实体类
 */
@Setter
@Data
@Getter
public class SysRole implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色ID
     */
    private Long id;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 角色编码
     */
    private String roleCode;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 角色拥有的权限ID列表 (非数据库字段)
     */
    private List<Long> permissionIds;

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
} 