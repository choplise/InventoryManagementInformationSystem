package org.shixuan.inventory.enums;

import lombok.Getter;

/**
 * 统一返回结果状态码
 */
@Getter
public enum ResultCode {
    
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    
    // 用户相关错误 1000-1999
    USER_NOT_EXIST(1000, "用户不存在"),
    USER_LOGIN_FAILED(1001, "用户名或密码错误"),
    USER_ACCOUNT_FORBIDDEN(1002, "用户已被禁用"),
    USER_NOT_LOGIN(1003, "用户未登录"),
    
    // 商品相关错误 2000-2999
    PRODUCT_NOT_EXIST(2000, "商品不存在"),
    PRODUCT_STOCK_ERROR(2001, "商品库存不足"),
    PRODUCT_CATEGORY_NOT_EXIST(2002, "商品分类不存在"),
    
    // 订单相关错误 3000-3999
    ORDER_NOT_EXIST(3000, "订单不存在"),
    ORDER_STATUS_ERROR(3001, "订单状态不正确"),
    
    // 库存相关错误 4000-4999
    INVENTORY_NOT_ENOUGH(4000, "库存不足"),
    INVENTORY_UPDATE_FAILED(4001, "库存更新失败"),
    INVENTORY_RECORD_NOT_FOUND(4002, "库存记录不存在"),
    
    // 供应商相关错误 5000-5999
    SUPPLIER_NOT_EXIST(5000, "供应商不存在"),
    
    // 客户相关错误 6000-6999
    CUSTOMER_NOT_EXIST(6000, "客户不存在"),
    
    // 采购模块
    PURCHASE_ORDER_NOT_EXIST(2001, "采购单不存在"),

    // 销售模块
    SALES_ORDER_NOT_EXIST(3001, "销售单不存在");

    
    private final int code;
    private final String message;
    
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    }