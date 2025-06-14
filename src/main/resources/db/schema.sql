-- ----------------------------
-- 数据库和用户创建 (可选, 如果数据库已存在则不需要)
-- ----------------------------
-- CREATE DATABASE IF NOT EXISTS inventory_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE inventory_system;

-- ----------------------------
-- 1. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 (1:启用, 0:禁用)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- 2. 角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`role_name`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- 3. 权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission_code` varchar(100) NOT NULL COMMENT '权限标识',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父权限ID',
  `type` tinyint(1) NOT NULL COMMENT '权限类型 (1:菜单, 2:按钮)',
  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- 4. 角色权限关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';


-- ----------------------------
-- 5. 商品分类表
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(100) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父分类ID',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';


-- ----------------------------
-- 6. 商品表
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_code` varchar(50) NOT NULL COMMENT '商品编码',
  `product_name` varchar(200) NOT NULL COMMENT '商品名称',
  `category_id` bigint(20) NOT NULL COMMENT '商品分类ID',
  `spec` varchar(100) DEFAULT NULL COMMENT '规格型号',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `purchase_price` decimal(10,2) DEFAULT '0.00' COMMENT '最新进价',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '销售价',
  `lower_limit` int(11) DEFAULT '0' COMMENT '库存下限',
  `upper_limit` int(11) DEFAULT '0' COMMENT '库存上限',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 (1:在售, 0:停产)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_code` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- 7. 供应商表
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_code` varchar(50) NOT NULL COMMENT '供应商编码',
  `supplier_name` varchar(200) NOT NULL COMMENT '供应商名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 (1:合作中, 0:停止合作)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_code` (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商表';

-- ----------------------------
-- 8. 客户表
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_code` varchar(50) NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) NOT NULL COMMENT '客户名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 (1:合作中, 0:停止合作)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_customer_code` (`customer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- ----------------------------
-- 9. 合同表 (简单示例)
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '合同ID',
  `contract_no` varchar(50) NOT NULL COMMENT '合同编号',
  `contract_name` varchar(200) NOT NULL COMMENT '合同名称',
  `party_a` varchar(200) DEFAULT NULL COMMENT '甲方',
  `party_b` varchar(200) DEFAULT NULL COMMENT '乙方',
  `sign_date` date DEFAULT NULL COMMENT '签订日期',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  `expiry_date` date DEFAULT NULL COMMENT '失效日期',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '合同金额',
  `content` text COMMENT '合同内容',
  `file_url` varchar(500) DEFAULT NULL COMMENT '附件URL',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 (1:执行中, 2:已完成, 0:已作废)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_contract_no` (`contract_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同表';

-- ----------------------------
-- 10. 采购订单表
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order`;
CREATE TABLE `purchase_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `supplier_id` bigint(20) NOT NULL COMMENT '供应商ID',
  `order_date` date NOT NULL COMMENT '订单日期',
  `expected_delivery_date` date DEFAULT NULL COMMENT '预计交货日期',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '总金额',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 (0:待审核, 1:已审核, 2:部分入库, 3:全部入库, 4:已作废)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购订单表';

-- ----------------------------
-- 11. 采购订单明细表
-- ----------------------------
DROP TABLE IF EXISTS `purchase_item`;
CREATE TABLE `purchase_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '采购订单ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '采购数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '采购单价',
  `amount` decimal(12,2) GENERATED ALWAYS AS (`quantity` * `unit_price`) STORED,
  `received_quantity` int(11) DEFAULT '0' COMMENT '已入库数量',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购订单明细表';

-- ----------------------------
-- 12. 采购入库单
-- ----------------------------
DROP TABLE IF EXISTS `purchase_receipt`;
CREATE TABLE `purchase_receipt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `receipt_no` varchar(50) NOT NULL COMMENT '入库单号',
  `purchase_order_id` bigint(20) NOT NULL COMMENT '采购订单ID',
  `receipt_date` datetime NOT NULL COMMENT '入库日期',
  `handler_id` bigint(20) DEFAULT NULL COMMENT '经手人ID',
  `remark` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_receipt_no` (`receipt_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库单';

-- ----------------------------
-- 13. 采购入库单明细
-- ----------------------------
DROP TABLE IF EXISTS `purchase_receipt_item`;
CREATE TABLE `purchase_receipt_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint(20) NOT NULL COMMENT '入库单ID',
  `purchase_item_id` bigint(20) NOT NULL COMMENT '采购订单明细ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '本次入库数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '采购单价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库单明细';

-- ----------------------------
-- 14. 采购退货单
-- ----------------------------
DROP TABLE IF EXISTS `purchase_return`;
CREATE TABLE `purchase_return` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `return_no` varchar(50) NOT NULL COMMENT '退货单号',
  `supplier_id` bigint(20) NOT NULL COMMENT '供应商ID',
  `return_date` date NOT NULL COMMENT '退货日期',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '退货总金额',
  `handler_id` bigint(20) DEFAULT NULL COMMENT '经手人ID',
  `remark` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_return_no` (`return_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购退货单';

-- ----------------------------
-- 15. 采购退货单明细
-- ----------------------------
DROP TABLE IF EXISTS `purchase_return_item`;
CREATE TABLE `purchase_return_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `return_id` bigint(20) NOT NULL COMMENT '退货单ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '退货数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '退货单价',
  `amount` decimal(12,2) GENERATED ALWAYS AS (`quantity` * `unit_price`) STORED,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购退货单明细';

-- ----------------------------
-- 16. 销售订单表
-- ----------------------------
DROP TABLE IF EXISTS `sales_order`;
CREATE TABLE `sales_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `order_date` date NOT NULL COMMENT '订单日期',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '总金额',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 (0:待审核, 1:已审核, 2:部分出库, 3:全部出库, 4:已作废)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单表';

-- ----------------------------
-- 17. 销售订单明细表
-- ----------------------------
DROP TABLE IF EXISTS `sales_item`;
CREATE TABLE `sales_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '销售订单ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '销售数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '销售单价',
  `amount` decimal(12,2) GENERATED ALWAYS AS (`quantity` * `unit_price`) STORED,
  `shipped_quantity` int(11) DEFAULT '0' COMMENT '已出库数量',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单明细表';

-- ----------------------------
-- 18. 销售出库单
-- ----------------------------
DROP TABLE IF EXISTS `sales_shipment`;
CREATE TABLE `sales_shipment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shipment_no` varchar(50) NOT NULL COMMENT '出库单号',
  `sales_order_id` bigint(20) NOT NULL COMMENT '销售订单ID',
  `shipment_date` datetime NOT NULL COMMENT '出库日期',
  `handler_id` bigint(20) DEFAULT NULL COMMENT '经手人ID',
  `remark` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shipment_no` (`shipment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库单';

-- ----------------------------
-- 19. 销售出库单明细
-- ----------------------------
DROP TABLE IF EXISTS `sales_shipment_item`;
CREATE TABLE `sales_shipment_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shipment_id` bigint(20) NOT NULL COMMENT '出库单ID',
  `sales_item_id` bigint(20) NOT NULL COMMENT '销售订单明细ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '本次出库数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '销售单价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库单明细';

-- ----------------------------
-- 20. 销售退货单
-- ----------------------------
DROP TABLE IF EXISTS `sales_return`;
CREATE TABLE `sales_return` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `return_no` varchar(50) NOT NULL COMMENT '退货单号',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `return_date` date NOT NULL COMMENT '退货日期',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '退货总金额',
  `handler_id` bigint(20) DEFAULT NULL COMMENT '经手人ID',
  `remark` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_return_no` (`return_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售退货单';

-- ----------------------------
-- 21. 销售退货单明细
-- ----------------------------
DROP TABLE IF EXISTS `sales_return_item`;
CREATE TABLE `sales_return_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `return_id` bigint(20) NOT NULL COMMENT '退货单ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '退货数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '退货单价',
  `amount` decimal(12,2) GENERATED ALWAYS AS (`quantity` * `unit_price`) STORED,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售退货单明细';

-- ----------------------------
-- 22. 库存表
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

-- ----------------------------
-- 23. 库存变动记录表
-- ----------------------------
DROP TABLE IF EXISTS `inventory_record`;
CREATE TABLE `inventory_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `change_quantity` int(11) NOT NULL COMMENT '变动数量 (正数表示入库, 负数表示出库)',
  `quantity_after_change` int(11) NOT NULL COMMENT '变动后数量',
  `change_type` tinyint(2) NOT NULL COMMENT '变动类型 (1:采购入库, 2:销售出库, 3:采购退货, 4:销售退货, 5:库存盘点)',
  `related_order_no` varchar(50) DEFAULT NULL COMMENT '关联单据号',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `operate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存变动记录表';

-- ----------------------------
-- 24. 库存盘点表
-- ----------------------------
DROP TABLE IF EXISTS `inventory_check`;
CREATE TABLE `inventory_check` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `check_no` varchar(50) NOT NULL COMMENT '盘点单号',
  `check_date` datetime NOT NULL COMMENT '盘点日期',
  `handler_id` bigint(20) DEFAULT NULL COMMENT '经手人ID',
  `remark` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_check_no` (`check_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存盘点表';

-- ----------------------------
-- 25. 库存盘点明细表
-- ----------------------------
DROP TABLE IF EXISTS `inventory_check_item`;
CREATE TABLE `inventory_check_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `check_id` bigint(20) NOT NULL COMMENT '盘点单ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `book_quantity` int(11) NOT NULL COMMENT '账面数量',
  `actual_quantity` int(11) NOT NULL COMMENT '实盘数量',
  `difference` int(11) GENERATED ALWAYS AS (`actual_quantity` - `book_quantity`) STORED COMMENT '差异数量 (盘盈为正, 盘亏为负)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存盘点明细表';

-- ----------------------------
-- 26. 财务月结表
-- ----------------------------
DROP TABLE IF EXISTS `finance_monthly_statement`;
CREATE TABLE `finance_monthly_statement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `year` int(4) NOT NULL COMMENT '年份',
  `month` int(2) NOT NULL COMMENT '月份',
  `opening_quantity` int(11) DEFAULT '0' COMMENT '期初数量',
  `opening_amount` decimal(12,2) DEFAULT '0.00' COMMENT '期初金额',
  `purchase_quantity` int(11) DEFAULT '0' COMMENT '本期入库数量',
  `purchase_amount` decimal(12,2) DEFAULT '0.00' COMMENT '本期入库金额',
  `sales_quantity` int(11) DEFAULT '0' COMMENT '本期销售数量',
  `sales_amount` decimal(12,2) DEFAULT '0.00' COMMENT '本期销售金额',
  `closing_quantity` int(11) DEFAULT '0' COMMENT '期末结存数量',
  `closing_amount` decimal(12,2) DEFAULT '0.00' COMMENT '期末结存金额',
  `cost_of_goods_sold` decimal(12,2) DEFAULT '0.00' COMMENT '本期销售成本(按加权平均法计算)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_year_month` (`product_id`,`year`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务月结表'; 