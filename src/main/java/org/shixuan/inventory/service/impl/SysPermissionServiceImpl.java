package org.shixuan.inventory.service.impl;

import org.shixuan.inventory.domain.SysPermission;
import org.shixuan.inventory.mapper.SysPermissionMapper;
import org.shixuan.inventory.service.SysPermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    private final SysPermissionMapper sysPermissionMapper;

    public SysPermissionServiceImpl(SysPermissionMapper sysPermissionMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
    }

    @Override
    public List<SysPermission> listPermissionsAsTree() {
        List<SysPermission> allPermissions = sysPermissionMapper.findAll();
        return buildTree(allPermissions);
    }

    @Override
    public SysPermission createPermission(SysPermission permission) {
        sysPermissionMapper.insert(permission);
        return permission;
    }

    @Override
    public SysPermission updatePermission(SysPermission permission) {
        sysPermissionMapper.update(permission);
        return permission;
    }

    @Override
    public void deletePermission(Long id) {
        // 在删除前，需要检查是否有子权限，实际应用中可能需要更复杂的逻辑
        sysPermissionMapper.deleteById(id);
    }

    private List<SysPermission> buildTree(List<SysPermission> permissions) {
        Map<Long, SysPermission> permissionMap = permissions.stream()
                .collect(Collectors.toMap(SysPermission::getId, p -> p));

        List<SysPermission> tree = new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (permission.getParentId() == 0) {
                tree.add(permission);
            } else {
                SysPermission parent = permissionMap.get(permission.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(permission);
                }
            }
        }
        return tree;
    }
} 