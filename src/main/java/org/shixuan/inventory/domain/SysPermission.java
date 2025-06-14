package org.shixuan.inventory.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 系统权限实体类
 */
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String permissionName;
    private String permissionCode;
    private Long parentId;
    /**
     * 权限类型：1为目录，2为菜单/按钮
     */
    private Integer type;

    /**
     * 子权限列表，用于构建树形结构
     */
    private List<SysPermission> children;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<SysPermission> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermission> children) {
        this.children = children;
    }
} 