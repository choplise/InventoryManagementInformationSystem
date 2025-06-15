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
- **描述**: 根据条件分页查询商品列表,支持模糊查询(keyword)和分类筛选(categoryId)(这两个是可选)。
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
    - **`GET /product/code/{productCode}`**: 根据商品编码获取商品信息。
      - **成功响应** (`200 OK`):
        ```json
        {
            "code": 200,
            "message": "操作成功",
            "data": {
                "id": 1,
                "productCode": "P001",
                "productName": "晨光中性笔",
                "categoryId": 4,
                "spec": "0.5mm 黑色",
                "unit": "支",
                "purchasePrice": 1.50,
                "salePrice": 3.50,
                "lowerLimit": 50,
                "upperLimit": 500,
                "status": 1,
                "remark": null,
                "createTime": "2025-06-12T08:00:37.000+00:00",
                "updateTime": "2025-06-14T07:45:01.000+00:00",
                "categoryName": "笔",
                "stockQuantity": 100
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
- **'PUT /product/{id}/status/{status}'**: 修改商品状态（启用1/禁用0）。
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": true}`
---
### 3. 库存管理 (Inventory)
- **`GET /inventory/check`**: 检查库存是否充足
  - **成功响应** (`200 OK`):
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": true
  }
  ```
- **`GET /inventory/page`**: 分页查询库存列表。
  - **请求示例**: `/api/inventory/page?pageNum=1&pageSize=10&keyword=P001&warningStatus=0`
  - 后两个是可选字段，预警选项 0: 不显示预警, 1: 显示低于下限, 2: 显示高于上限
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
- **`GET /inventory/record/page`**: 查询单个商品的库存变动记录。
  - **请求示例**: `/api/inventory/record/page?pageNum=1&pageSize=10`
  - **可选字段**: `productId` (商品ID), `changeType`(改变类型), `startTime`, `endTime`
  - **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "操作成功",
      "data": {
          "total": 5,
          "list": [
              {
                "id": 1,
                "productId": 1,
                "changeQuantity": 100,
                "quantityAfterChange": 100,
                "changeType": 1,
                "relatedOrderNo": "INIT001",
                "operatorId": 1,
                "operateTime": "2025-06-12T08:00:37.000+00:00",
                "productCode": "P001",
                "productName": "晨光中性笔",
                "operatorName": "管理员"
              }
          ],
          "pageNum": 1,
          "pageSize": 1,
           "pages": 5
      }
  }
  ```
--**`GET /inventory/product/{productId}`**: 根据Id获取单个商品库存详情。
- **请求示例**: `/api/inventory/product/1`
- **成功响应**: (单个库存对象，结构同上)

- **`GET /inventory/warnings`**: 查询库存预警信息。
  - **请求示例**: `/api/inventory/warning`
    - **成功响应** (`200 OK`):
    ```json
    {
        "code": 200,
        "message": "操作成功",
        "data": [
            {
                "upperLimit": [],
                "lowerLimit": []
            }
        ]
    }
    ```
- **`POST /inventory/increase`**: 执行库存入库操作。
  - **请求体**: `{"productId": 1, "quantity": 100, "changeType": """operatorId": 1, "relatedOrderNo": "PO20231027001"}`
  - 变动类型 (1:采购入库, 2:销售出库, 3:采购退货, 4:销售退货, 5:库存盘点)
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

- **`POST /inventory/decrease`**: 执行库存出库操作。
  - **请求体**: `{"productId": 1, "quantity": 50, "changeType": 2, "operatorId": 1, "relatedOrderNo": "SO20231028001"}`
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

### 4. 供应商管理 (Supplier)

- **`GET /supplier/page`**: 分页查询供应商。
  - **请求示例**: `/api/supplier/page?pageNum=1&pageSize=10&keyword=晨光`(关键字可选)
  - **成功响应**: (结构类似商品分页，`list`中为供应商对象)
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
                "supplierCode": "S001",
                "supplierName": "晨光文具股份有限公司",
                "contact": "苏帝",
                "phone": "15759787668",
                "address": "",
                "email": "",
                "status": 1,
                "remark": "用文具自慰的",
                "createTime": "2025-06-12T08:00:37.000+00:00",
                "updateTime": "2025-06-14T08:19:34.000+00:00"
            }
        ],
        "pageNum": 1,
        "pageSize": 5,
        "pages": 1
      }
  }
  ```
- **`GET /supplier/list`**: 获取所有供应商列表（用于下拉选择）。
- **`GET /supplier/{id}`**: 获取供应商详情。
- **`POST /supplier`**: 新增供应商。
  - **请求体**: `{"supplierCode": "S001", "supplierName": "上海晨光文具", "contact": "张三", "phone": "13800138000"}`
- **`PUT /supplier/{id}`**: 修改供应商。
- **`DELETE /supplier/{id}`**: 删除供应商。

---

### 5. 采购管理 (Purchase)

#### 5.1 分页查询采购单
- **`GET /purchase/page`**
- **描述**: 根据条件分页查询采购订单列表。
- **请求参数**:
  - `pageNum` (可选, 默认1): 页码
  - `pageSize` (可选, 默认10): 每页数量
  - `orderNo` (可选): 订单号 (模糊查询)
  - `supplierId` (可选): 供应商ID
  - `status` (可选): 订单状态 (0:待审核, 1:已审核, 2:全部入库, 3:部分入库, 4:已作废)
  - `startDate` (可选, yyyy-MM-dd): 订单日期范围（开始）
  - `endDate` (可选, yyyy-MM-dd): 订单日期范围（结束）
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
                  "orderNo": "PO20231027001",
                  "supplierId": 1,
                  "creatorId": 1,
                  "orderDate": "2023-10-27",
                  "totalAmount": 550.00,
                  "status": 1,
                  "supplierName": "上海晨光文具",
                  "creatorName": "admin"
              }
          ],
          "pageNum": 1,
          "pageSize": 10
      }
  }
  ```

#### 5.2 获取采购单详情
- **`GET /purchase/{id}`**
- **描述**: 根据ID获取单个采购订单的详细信息，包含商品明细。
- **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "操作成功",
      "data": {
          "id": 1,
          "orderNo": "PO20231027001",
          "supplierId": 1,
          "creatorId": 1,
          "orderDate": "2023-10-27",
          "totalAmount": 550.00,
          "status": 1,
          "remark": "常规采购",
          "supplierName": "上海晨光文具",
          "creatorName": "admin",
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
  }
  ```

#### 5.3 创建采购单
- **`POST /purchase`**
- **描述**: 创建一个新的采购订单。`creatorId` 应为当前操作员ID。
- **请求体** (`application/json`):
  ```json
  {
    "supplierId": 1,
    "creatorId": 1,
    "orderDate": "2023-10-27",
    "remark": "常规采购",
    "items": [
      {
        "productId": 1,
        "quantity": 100,
        "purchasePrice": 5.50
      }
    ]
  }
  ```
- **成功响应**: (`200 OK`, 返回创建后的采购单详情，结构同5.2)

#### 5.4 更新采购单
- **`PUT /purchase/{id}`**
- **描述**: 更新一个**未审核**的采购订单。
- **请求体**: (结构同5.3)
- **成功响应**: (`200 OK`, 返回更新后的采购单详情)

#### 5.5 审核采购单
- **`POST /purchase/{id}/approve`**
- **描述**: 审核一个**待审核**的采购订单。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

#### 5.6 执行采购入库
- **`POST /purchase/{id}/stock-in`**
- **描述**: 对**已审核**的采购订单执行入库操作。
- **请求体**: `{"operatorId": 1}` (此ID为当前操作员ID)
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

#### 5.7 作废采购单
- **`POST /purchase/{id}/cancel`**
- **描述**: 作废一个采购订单。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

#### 5.8 采购退货

##### 5.8.1 分页查询采购退货单
- **`GET /purchase-returns`**
- **描述**: 根据条件分页查询采购退货单列表。
- **请求参数 (Query)**:
  - `pageNum` (可选, 默认1): 页码
  - `pageSize` (可选, 默认10): 每页数量
  - `returnNo` (可选): 退货单号 (模糊查询)
  - `supplierId` (可选): 供应商ID
  - `status` (可选): 订单状态 (0:待审核, 1:已审核, 2:已出库, -1:已作废)
  - `startDate` (可选, yyyy-MM-dd): 退货日期范围（开始）
  - `endDate` (可选, yyyy-MM-dd): 退货日期范围（结束）
- **请求示例**: `/api/purchase-returns?pageNum=1&pageSize=10&status=1`
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
                  "returnNo": "PR20231101001",
                  "purchaseOrderId": 10,
                  "supplierId": 1,
                  "creatorId": 1,
                  "returnDate": "2023-11-01",
                  "totalAmount": 110.00,
                  "status": 1,
                  "supplierName": "上海晨光文具",
                  "creatorName": "admin"
              }
          ],
          "pageNum": 1,
          "pageSize": 10
      }
  }
  ```

##### 5.8.2 获取采购退货单详情
- **`GET /purchase-returns/{id}`**
- **描述**: 根据ID获取单个采购退货单的详细信息，包含商品明细。
- **请求示例**: `/api/purchase-returns/1`
- **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "操作成功",
      "data": {
          "id": 1,
          "returnNo": "PR20231101001",
          "purchaseOrderId": 10,
          "supplierId": 1,
          "creatorId": 1,
          "returnDate": "2023-11-01",
          "totalAmount": 110.00,
          "status": 1,
          "remark": "质量问题退货",
          "supplierName": "上海晨光文具",
          "creatorName": "admin",
          "items": [
              {
                  "productId": 1,
                  "quantity": 20,
                  "purchasePrice": 5.50,
                  "amount": 110.00,
                  "productName": "晨光笔记本"
              }
          ]
      }
  }
  ```

##### 5.8.3 创建采购退货单
- **`POST /purchase-returns`**
- **描述**: 创建一个新的采购退货单。`creatorId` 应为当前操作员ID。
- **请求体** (`application/json`): `PurchaseReturnDTO`
  ```json
  {
    "purchaseOrderId": 10,
    "supplierId": 1,
    "creatorId": 1,
    "returnDate": "2023-11-01",
    "remark": "质量问题退货",
    "items": [
      {
        "productId": 1,
        "quantity": 20,
        "purchasePrice": 5.50
      }
    ]
  }
  ```
- **成功响应**: (`200 OK`, 返回创建后的采购退货单详情，结构同5.8.2)

##### 5.8.4 更新采购退货单
- **`PUT /purchase-returns/{id}`**
- **描述**: 更新一个**未审核**的采购退货单。
- **请求体**: (结构同5.8.3)
- **成功响应**: (`200 OK`, 返回更新后的采购退货单详情)

##### 5.8.5 审核采购退货单
- **`POST /purchase-returns/{id}/audit`**
- **描述**: 审核一个**待审核**的采购退货单，审核后将扣减库存。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

##### 5.8.6 删除采购退货单
- **`DELETE /purchase-returns/{id}`**
- **描述**: 删除一个采购退货单，仅**未审核**的单据可删除。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

---

### 6. 销售管理 (Sales)

#### 6.1 分页查询销售单
- **`GET /sales/page`**
- **描述**: 根据条件分页查询销售订单列表。
- **请求参数**:
  - `pageNum` (可选, 默认1): 页码
  - `pageSize` (可选, 默认10): 每页数量
  - `orderNo` (可选): 订单号 (模糊查询)
  - `customerId` (可选): 客户ID
  - `status` (可选): 订单状态 (0:待审核, 1:已审核, 2:已出库, -1:已作废)
  - `startDate` (可选, yyyy-MM-dd): 订单日期范围（开始）
  - `endDate` (可选, yyyy-MM-dd): 订单日期范围（结束）
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
                  "orderNo": "SO20231028001",
                  "customerId": 2,
                  "creatorId": 1,
                  "orderDate": "2023-10-28",
                  "totalAmount": 1600.00,
                  "status": 1,
                  "customerName": "某客户公司",
                  "creatorName": "admin"
              }
          ],
          "pageNum": 1,
          "pageSize": 10
      }
  }
  ```

#### 6.2 获取销售单详情
- **`GET /sales/{id}`**
- **描述**: 根据ID获取单个销售订单的详细信息，包含商品明细。
- **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "操作成功",
      "data": {
          "id": 1,
          "orderNo": "SO20231028001",
          "customerId": 2,
          "creatorId": 1,
          "orderDate": "2023-10-28",
          "totalAmount": 1600.00,
          "status": 1,
          "remark": "加急订单",
          "customerName": "某客户公司",
          "creatorName": "admin",
          "items": [
              {
                  "productId": 1,
                  "quantity": 20,
                  "salePrice": 8.00,
                  "amount": 160.00,
                  "productCode": "P001",
                  "productName": "晨光笔记本",
                  "spec": "A5",
                  "unit": "本"
              }
          ]
      }
  }
  ```

#### 6.3 创建销售单
- **`POST /sales`**
- **描述**: 创建一个新的销售订单。注意 `creatorId` 应为当前操作员ID，前端需要在调用此接口时传入。
- **请求体** (`application/json`):
  ```json
  {
    "customerId": 2,
    "creatorId": 1,
    "orderDate": "2023-10-28",
    "remark": "加急订单",
    "items": [
      {
        "productId": 1,
        "quantity": 20,
        "salePrice": 8.00
      },
      {
        "productId": 2,
        "quantity": 50,
        "salePrice": 2.50
      }
    ]
  }
  ```
- **成功响应**: (`200 OK`, 返回创建后的销售单详情，结构同6.2)

#### 6.4 更新销售单
- **`PUT /sales/{id}`**
- **描述**: 更新一个已存在但**未审核**的销售订单。
- **请求体**: (结构同6.3，可只包含需要修改的字段)
- **成功响应**: (`200 OK`, 返回更新后的销售单详情，结构同6.2)

#### 6.5 审核销售单
- **`POST /sales/{id}/approve`**
- **描述**: 审核一个**待审核**状态的销售订单，将其状态变更为"已审核"。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

#### 6.6 执行销售出库
- **`POST /sales/{id}/stock-out`**
- **描述**: 对**已审核**的销售订单执行出库操作，会扣减相应商品库存,id为当前操作员ID。
- **请求体** (`application/json`):
  ```json
  {
    "operatorId": 1
  }
  ```
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

#### 6.7 作废销售单
- **`POST /sales/{id}/cancel`**
- **描述**: 作废一个销售订单。已出库的订单不能作废。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

#### 6.8 销售退货

##### 6.8.1 分页查询销售退货单
- **`GET /sales-returns`**
- **描述**: 根据条件分页查询销售退货单列表。
- **请求参数 (Query)**:
  - `pageNum` (可选, 默认1): 页码
  - `pageSize` (可选, 默认10): 每页数量
  - `returnNo` (可选): 退货单号 (模糊查询)
  - `customerId` (可选): 客户ID
  - `status` (可选): 订单状态 (0:待审核, 1:已审核, 2:已入库, -1:已作废)
  - `startDate` (可选, yyyy-MM-dd): 退货日期范围（开始）
  - `endDate` (可选, yyyy-MM-dd): 退货日期范围（结束）
- **请求示例**: `/api/sales-returns?pageNum=1&pageSize=10&status=1`
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
                  "returnNo": "SR20231101001",
                  "salesOrderId": 25,
                  "customerId": 2,
                  "creatorId": 1,
                  "returnDate": "2023-11-01",
                  "totalAmount": 160.00,
                  "status": 1,
                  "customerName": "某客户公司",
                  "creatorName": "admin"
              }
          ],
          "pageNum": 1,
          "pageSize": 10
      }
  }
  ```

##### 6.8.2 获取销售退货单详情
- **`GET /sales-returns/{id}`**
- **描述**: 根据ID获取单个销售退货单的详细信息，包含商品明细。
- **请求示例**: `/api/sales-returns/1`
- **成功响应** (`200 OK`):
  ```json
  {
      "code": 200,
      "message": "操作成功",
      "data": {
          "id": 1,
          "returnNo": "SR20231101001",
          "salesOrderId": 25,
          "customerId": 2,
          "creatorId": 1,
          "returnDate": "2023-11-01",
          "totalAmount": 160.00,
          "status": 1,
          "remark": "客户退货",
          "customerName": "某客户公司",
          "creatorName": "admin",
          "items": [
              {
                  "productId": 1,
                  "quantity": 20,
                  "salePrice": 8.00,
                  "amount": 160.00,
                  "productName": "晨光笔记本"
              }
          ]
      }
  }
  ```

##### 6.8.3 创建销售退货单
- **`POST /sales-returns`**
- **描述**: 创建一个新的销售退货单。`creatorId` 应为当前操作员ID。
- **请求体** (`application/json`): `SalesReturnDTO`
  ```json
  {
    "salesOrderId": 25,
    "customerId": 2,
    "creatorId": 1,
    "returnDate": "2023-11-01",
    "remark": "客户退货",
    "items": [
      {
        "productId": 1,
        "quantity": 20,
        "salePrice": 8.00
      }
    ]
  }
  ```
- **成功响应**: (`200 OK`, 返回创建后的销售退货单详情，结构同6.8.2)

##### 6.8.4 更新销售退货单
- **`PUT /sales-returns/{id}`**
- **描述**: 更新一个**未审核**的销售退货单。
- **请求体**: (结构同6.8.3)
- **成功响应**: (`200 OK`, 返回更新后的销售退货单详情)

##### 6.8.5 审核销售退货单
- **`POST /sales-returns/{id}/audit`**
- **描述**: 审核一个**待审核**的销售退货单，审核后将增加库存。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

##### 6.8.6 删除销售退货单
- **`DELETE /sales-returns/{id}`**
- **描述**: 删除一个销售退货单，仅**未审核**的单据可删除。
- **成功响应**: `{"code": 200, "message": "操作成功", "data": null}`

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

---

### 8. 客户管理 (Customer)

- **`GET /customer/page`**
  - **描述**: 分页查询客户列表，支持按关键字模糊搜索。
  - **请求示例**: `/api/customer/page?pageNum=1&pageSize=10&keyword=某公司`
  - **成功响应** (`200 OK`):
    ```json
    {
        "code": 200,
        "message": "操作成功",
        "data": {
            "total": 1,
            "list": [
                {
                    "id": 2,
                    "customerCode": "C002",
                    "customerName": "某客户公司",
                    "contact": "李四",
                    "phone": "13900139000",
                    "address": "客户地址",
                    "email": "customer@example.com",
                    "status": 1
                }
            ],
            "pageNum": 1,
            "pageSize": 10
        }
    }
    ```

- **`GET /customer/list`**
  - **描述**: 获取所有客户的列表，通常用于下拉选择框。
  - **成功响应**: 返回客户对象数组 `[{"id": 1, "customerName": "客户A"}, ...]`

  - **请求体** (`application/json`):
    ```json
    {
      "customerCode": "C003",
      "customerName": "新客户",
      "contact": "王五",
      "phone": "13700137000"
    }
    ```
  - **成功响应**: `{"code": 200, "message": "操作成功", "data": 3}` (返回新客户的ID)

- **`PUT /customer/{id}`**
  - **描述**: 更新指定ID的客户信息。
  - **请求体**: (结构同上, 包含需要更新的字段)

- **`DELETE /customer/{id}`**
  - **描述**: 删除指定ID的客户。

---

### 9. 销售退货管理 (Sales Return)

- **`GET /sales-returns`**: 查询销售退货单列表。
  - **请求参数**: `returnNo` (退货单号), `customerId` (客户ID), `status` (状态), `startDate`, `endDate`
- **`GET /sales-returns/{id}`**: 获取销售退货单详情。
- **`POST /sales-returns`**: 创建销售退货单。
  - **请求体**: `SalesReturnDTO` 对象。
- **`PUT /sales-returns/{id}`**: 更新销售退货单。
  - **请求体**: `SalesReturnDTO` 对象。
- **`DELETE /sales-returns/{id}`**: 删除销售退货单。
- **`POST /sales-returns/{id}/audit`**: 审核销售退货单。

---

### 10. 采购退货管理 (Purchase Return)

- **`GET /purchase-returns`**: 查询采购退货单列表。
  - **请求参数**: `returnNo` (退货单号), `supplierId` (供应商ID), `status` (状态), `startDate`, `endDate`
- **`GET /purchase-returns/{id}`**: 获取采购退货单详情。
- **`POST /purchase-returns`**: 创建采购退货单。
  - **请求体**: `PurchaseReturnDTO` 对象。
- **`PUT /purchase-returns/{id}`**: 更新采购退货单。
  - **请求体**: `PurchaseReturnDTO` 对象。
- **`DELETE /purchase-returns/{id}`**: 删除采购退货单。
- **`POST /purchase-returns/{id}/audit`**: 审核采购退货单。

## 系统部署
1. 克隆项目到本地
2. 配置application.yml中的数据库连接
3. 执行SQL脚本创建数据库和表
4. 使用Maven打包项目
5. 运行jar包启动项目

## 开发团队
- 技术支持：朱世轩
- 联系邮箱：1984057971@qq.com 
