# 进销存管理系统

## 项目介绍
本系统是一个企业进销存管理系统，旨在帮助企业高效管理商品的进货、销售和库存，提高管理效率，避免传统人工管理方式的不足。

## 技术栈
- 后端：Spring Boot 2.7.x + MyBatis
- 数据库：MySQL 8.0
- 项目构建：Maven

## 系统功能
1. **资料管理**：维护商品资料、供货商资料、客户资料等基础数据和合同信息
2. **采购管理**：管理采购订单、进货单、退货信息、进货价格等
3. **销售管理**：管理销售订单、销售退货单、历史查询等
4. **库存管理**：库存查询、库存盘点、库存上下限报警等
5. **账务管理**：月度结账操作，计算商品成本的进、销、结存情况
6. **系统管理**：参数设置、权限设置、密码管理等

## 数据库设计
数据库设计遵循第三范式，主要包含以下数据表：
- 用户表 (sys_user)
- 角色表 (sys_role)
- 权限表 (sys_permission)
- 商品表 (product)
- 供应商表 (supplier)
- 客户表 (customer)
- 合同表 (contract)
- 采购订单表 (purchase_order)
- 采购明细表 (purchase_item)
- 采购退货表 (purchase_return)
- 销售订单表 (sales_order)
- 销售明细表 (sales_item)
- 销售退货表 (sales_return)
- 库存表 (inventory)
- 库存变动记录表 (inventory_record)
- 财务月结表 (finance_monthly)

## API文档

所有API的基础路径为 `/api`。

---

### 1. 认证模块 (Auth)

#### 1.1 用户登录

- **`POST /auth/login`**
- **描述**: 用户通过用户名和密码进行登录，成功后返回Token。
- **请求体** (`application/json`):
  ```json
  {
    "username": "admin",
    "password": "123456"
  }
  ```
- **成功响应** (`200 OK`):
  ```json
  {
    "code": 200,
    "message": "登录成功",
    "data": {
      "token": "eyJhbGciOiJIUzI1NiJ9...",
      "userInfo": {
        "id": 1,
        "username": "admin",
        "realName": "超级管理员",
        "roleId": 1,
        "roleName": "超级管理员"
      }
    }
  }
  ```

#### 1.2 获取当前用户信息
- **`GET /auth/info`**
- **描述**: 获取当前登录用户的信息。请求头中需要携带有效的JWT `token`。
- **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "操作成功",
      "data": {
          "id": 1,
          "username": "admin",
          "realName": "超级管理员",
          "roleId": 1,
          "roleName": "超级管理员"
      }
  }
  ```

#### 1.3 修改密码
- **`POST /auth/updatePassword`**
- **描述**: 修改当前登录用户的密码。
- **请求体** (`application/json`):
  ```json
  {
      "oldPassword": "123456",
      "newPassword": "newPassword123"
  }
  ```
- **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "密码修改成功",
      "data": null
  }
  ```
---

### 2. 商品资料管理 (Product)

#### 2.1 商品分类

- **`GET /category/tree`**: 获取树形的商品分类列表。
  - **成功响应** (`200 OK`):
    ```json
    {
        "code": 200,
        "message": "操作成功",
        "data": [
            {
                "id": 1,
                "categoryName": "办公用品",
                "parentId": 0,
                "sortOrder": 1,
                "children": [
                    {
                        "id": 3,
                        "categoryName": "笔类",
                        "parentId": 1,
                        "sortOrder": 1,
                        "children": []
                    }
                ]
            }
        ]
    }
    ```
- **`POST /category`**: 新增商品分类。
  - **请求体**: `{"categoryName": "新增分类", "parentId": 1, "sortOrder": 10}`
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": 123}` (返回新ID)
- **`PUT /category/{id}`**: 修改商品分类。
  - **请求体**: `{"categoryName": "修改后的分类"}`
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`
- **`DELETE /category/{id}`**: 删除商品分类。
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

#### 2.2 商品信息

- **`GET /product/page`**: 分页查询商品。
  - **请求示例**: `/api/product/page?pageNum=1&pageSize=10&keyword=笔记本&categoryId=3`
  - **成功响应** (`200 OK`):
    ```json
    {
        "code": 200,
        "message": "操作成功",
        "data": {
            "total": 1,
            "list": [
                {
                    "id": 1,
                    "productCode": "P001",
                    "productName": "晨光笔记本",
                    "categoryId": 3,
                    "spec": "A5",
                    "unit": "本",
                    "purchasePrice": 5.50,
                    "salePrice": 8.00,
                    "lowerLimit": 10,
                    "upperLimit": 100,
                    "status": 1,
                    "categoryName": "笔类",
                    "stockQuantity": 50
                }
            ],
            "pageNum": 1,
            "pageSize": 10
        }
    }
    ```
- **`GET /product/{id}`**: 获取商品详情。
  - **成功响应**: (单个商品对象，结构同上)
- **`POST /product`**: 新增商品。
  - **请求体**: (单个商品对象，不含id, categoryName, stockQuantity)
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": 123}` (返回新ID)
- **`PUT /product/{id}`**: 修改商品。
  - **请求体**: (需要更新的商品字段)
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`
- **`DELETE /product/{id}`**: 删除商品。
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

---
### 3. 库存管理 (Inventory)

- **`GET /inventory/page`**: 分页查询库存列表。
  - **请求示例**: `/api/inventory/page?pageNum=1&pageSize=10&keyword=P001`
  - **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "操作成功",
      "data": {
          "total": 1,
          "list": [
              {
                  "id": 1,
                  "productId": 1,
                  "quantity": 50,
                  "productCode": "P001",
                  "productName": "晨光笔记本",
                  "unit": "本",
                  "lowerLimit": 10,
                  "upperLimit": 100,
                  "warningStatus": 0 
              }
          ],
          "pageNum": 1,
          "pageSize": 10
      }
  }
  ```
- **`GET /inventory/records`**: 查询单个商品的库存变动记录。
  - **请求示例**: `/api/inventory/records?pageNum=1&pageSize=10&productId=1`
- **`GET /inventory/warnings`**: 查询库存预警信息。
  - **请求示例**: `/api/inventory/warnings?pageNum=1&pageSize=10&warningStatus=1` (查询低于下限的)

---

### 4. 供应商管理 (Supplier)

- **`GET /supplier/page`**: 分页查询供应商。
  - **请求示例**: `/api/supplier/page?pageNum=1&pageSize=10&keyword=晨光`
  - **成功响应**: (结构类似商品分页，`list`中为供应商对象)
- **`GET /supplier/list`**: 获取所有供应商列表（用于下拉选择）。
- **`GET /supplier/{id}`**: 获取供应商详情。
- **`POST /supplier`**: 新增供应商。
  - **请求体**: `{"supplierCode": "S001", "supplierName": "上海晨光文具", "contact": "张三", "phone": "13800138000"}`
- **`PUT /supplier/{id}`**: 修改供应商。
- **`DELETE /supplier/{id}`**: 删除供应商。

---

### 5. 采购管理 (Purchase)

- **`GET /purchase/page`**: 分页查询采购单。
  - **请求示例**: `/api/purchase/page?pageNum=1&pageSize=10&status=1` (查询已审核的)
  - **成功响应** (`200 OK`): (PageResult, list中为PurchaseOrder对象)
    ```json
    {
        "id": 1,
        "orderNo": "PO20231027001",
        "supplierId": 1,
        "totalAmount": 550.00,
        "status": 1, // 0:待审核, 1:已审核, 2:已入库, -1:已作废
        "supplierName": "上海晨光文具",
        "purchaserName": "admin"
    }
    ```
- **`GET /purchase/{id}`**: 获取采购单详情（包含商品明细）。
  - **成功响应**: (单个PurchaseOrder对象，包含`items`列表)
    ```json
    {
      "id": 1,
      "items": [
        {
          "productId": 1,
          "quantity": 100,
          "purchasePrice": 5.50,
          "amount": 550.00,
          "productName": "晨光笔记本"
        }
      ]
    }
    ```
- **`POST /purchase`**: 创建采购单。
  - **请求体**: (PurchaseOrder对象，含`items`列表，不含id/orderNo等后台生成字段)
- **`PUT /purchase/{id}`**: 更新采购单（仅待审核状态）。
- **`POST /purchase/{id}/approve`**: 审核采购单。
- **`POST /purchase/{id}/stock-in`**: 执行采购入库。
  - **请求体**: `{"operatorId": 1}` (此ID为当前操作员ID)
- **`POST /purchase/{id}/cancel`**: 作废采购单。

---

### 6. 销售管理 (Sales)

销售管理API与采购管理类似。
- **`GET /sales/page`**: 分页查询销售单。
- **`GET /sales/{id}`**: 获取销售单详情。
- **`POST /sales`**: 创建销售单。
- **`PUT /sales/{id}`**: 更新销售单。
- **`POST /sales/{id}/approve`**: 审核销售单。
- **`POST /sales/{id}/stock-out`**: 执行销售出库。
  - **请求体**: `{"operatorId": 1}`
- **`POST /sales/{id}/cancel`**: 作废销售单。

---

### 7. 财务管理 (Financial)

- **`GET /financial/records`**: 查询财务流水。
  - **请求示例**: `/api/financial/records?startDate=2023-10-01&endDate=2023-10-31`
  - **成功响应** (`200 OK`):
  ```json
    {
      "code": 200,
      "message": "操作成功",
      "data": [
        {
          "type": "销售收入",
          "orderNo": "SO20231026001",
          "date": "2023-10-26",
          "amount": 800.00,
          "party": "某客户公司",
          "operator": "销售员A"
        },
        {
          "type": "采购支出",
          "orderNo": "PO20231027001",
          "date": "2023-10-27",
          "amount": -550.00,
          "party": "上海晨光文具",
          "operator": "admin"
        }
      ]
    }
  ```

## 系统部署
1. 克隆项目到本地
2. 配置application.yml中的数据库连接
3. 执行SQL脚本创建数据库和表
4. 使用Maven打包项目
5. 运行jar包启动项目

## 开发团队
- 技术支持：朱世轩
- 联系邮箱：1984057971@qq.com 