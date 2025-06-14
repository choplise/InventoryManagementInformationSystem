-- ----------------------------
-- 4. 初始化权限数据 (清空旧数据)
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `sys_permission`;
TRUNCATE TABLE `sys_role_permission`;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 插入新的权限数据 (树状结构)
-- ----------------------------
-- 系统管理 (ID: 1-10)
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `type`) VALUES
(1, '系统管理', 'sys', 0, 1),
(2, '用户管理', 'sys:user', 1, 1),
(3, '角色管理', 'sys:role', 1, 1),
(4, '权限管理', 'sys:permission', 1, 1),
(5, '分配权限', 'sys:role:assign_permissions', 3, 2);

-- 资料管理 (ID: 11-30)
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `type`) VALUES
(11, '资料管理', 'data', 0, 1),
(12, '商品管理', 'data:product', 11, 1),
(13, '新增商品', 'data:product:create', 12, 2),
(14, '修改商品', 'data:product:update', 12, 2),
(15, '删除商品', 'data:product:delete', 12, 2),
(16, '客户管理', 'data:customer', 11, 1),
(17, '供应商管理', 'data:supplier', 11, 1);

-- 业务管理 (ID: 31-50)
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `type`) VALUES
(31, '业务管理', 'business', 0, 1),
(32, '采购管理', 'business:purchase', 31, 1),
(33, '创建采购单', 'business:purchase:create', 32, 2),
(34, '审核采购单', 'business:purchase:approve', 32, 2),
(35, '采购单入库', 'business:purchase:stock-in', 32, 2),
(36, '销售管理', 'business:sales', 31, 1),
(37, '创建销售单', 'business:sales:create', 36, 2),
(38, '审核销售单', 'business:sales:approve', 36, 2),
(39, '销售单出库', 'business:sales:stock-out', 36, 2);

-- 库存管理 (ID: 51-60)
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `type`) VALUES
(51, '库存管理', 'inventory', 0, 1),
(52, '库存查询', 'inventory:query', 51, 1),
(53, '库存预警', 'inventory:warning', 51, 1);

-- 财务管理 (ID: 61-70)
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `type`) VALUES
(61, '财务管理', 'financial', 0, 1),
(62, '财务流水查询', 'financial:records', 61, 1);


-- ----------------------------
-- 5. 角色权限关系表 (重新关联)
-- 为超级管理员 (role_id=1) 分配所有新创建的权限
-- ----------------------------
INSERT INTO `sys_role_permission` (role_id, permission_id)
SELECT 1, id FROM `sys_permission`;

-- ----------------------------
-- 6. 初始化商品分类数据
-- ... existing code ... 