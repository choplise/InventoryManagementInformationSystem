-- ----------------------------
-- 初始化数据
-- ----------------------------

-- 用户和角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`) VALUES
(1, '超级管理员', 'ROLE_ADMIN', '系统最高权限'),
(2, '采购经理', 'ROLE_PURCHASE_MANAGER', '负责采购管理'),
(3, '销售经理', 'ROLE_SALES_MANAGER', '负责销售管理'),
(4, '库管员', 'ROLE_WAREHOUSE_KEEPER', '负责库存管理');

-- 初始用户: admin/123456, a/1 (BCrypt加密)
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `role_id`, `status`) VALUES
(1, 'admin', '$2a$10$w4rY.1zO/j1jZ7p5bJ.M.e9v.wY2U5.jO/T.L6k4G0G2p.Z5y.J/O', '管理员', 1, 1),
(2, 'purchase', '$2a$10$w4rY.1zO/j1jZ7p5bJ.M.e9v.wY2U5.jO/T.L6k4G0G2p.Z5y.J/O', '采购员张三', 2, 1),
(3, 'sales', '$2a$10$w4rY.1zO/j1jZ7p5bJ.M.e9v.wY2U5.jO/T.L6k4G0G2p.Z5y.J/O', '销售员李四', 3, 1),
(4, 'warehouse', '$2a$10$w4rY.1zO/j1jZ7p5bJ.M.e9v.wY2U5.jO/T.L6k4G0G2p.Z5y.J/O', '库管员王五', 4, 1);

-- 商品分类
INSERT INTO `product_category` (`id`, `category_name`, `parent_id`, `sort_order`) VALUES
(1, '办公用品', 0, 1),
(2, '电子产品', 0, 2),
(3, '食品饮料', 0, 3),
(4, '笔', 1, 1),
(5, '笔记本', 1, 2),
(6, '手机', 2, 1),
(7, '电脑', 2, 2);

-- 商品
INSERT INTO `product` (`id`, `product_code`, `product_name`, `category_id`, `spec`, `unit`, `purchase_price`, `sale_price`, `lower_limit`, `upper_limit`) VALUES
(1, 'P001', '晨光中性笔', 4, '0.5mm 黑色', '支', 1.50, 2.50, 50, 500),
(2, 'P002', '得力笔记本', 5, 'A5 60页', '本', 5.00, 8.00, 30, 300),
(3, 'P003', '华为Mate 60 Pro', 6, '12+512G 雅川青', '台', 6500.00, 6999.00, 5, 50),
(4, 'P004', '联想ThinkPad X1 Carbon', 7, '14英寸 i7/16G/1T', '台', 12000.00, 15000.00, 3, 30),
(5, 'P005', '农夫山泉', 3, '550ml', '瓶', 1.00, 2.00, 100, 1000);

-- 供应商
INSERT INTO `supplier` (`id`, `supplier_code`, `supplier_name`, `contact`, `phone`) VALUES
(1, 'S001', '晨光文具股份有限公司', '王经理', '13812345678'),
(2, 'S002', '深圳华为技术有限公司', '李总', '13987654321');

-- 客户
INSERT INTO `customer` (`id`, `customer_code`, `customer_name`, `contact`, `phone`) VALUES
(1, 'C001', 'A科技公司', '张三', '13500001111'),
(2, 'C002', 'B贸易公司', '李四', '13600002222');

-- 初始化库存
INSERT INTO `inventory` (`product_id`, `quantity`) VALUES
(1, 100),
(2, 50),
(3, 10),
(4, 5),
(5, 200);

-- 初始化库存变动记录
INSERT INTO `inventory_record` (`product_id`, `change_quantity`, `quantity_after_change`, `change_type`, `related_order_no`, `operator_id`) VALUES
(1, 100, 100, 1, 'INIT001', 1),
(2, 50, 50, 1, 'INIT001', 1),
(3, 10, 10, 1, 'INIT001', 1),
(4, 5, 5, 1, 'INIT001', 1),
(5, 200, 200, 1, 'INIT001', 1); 