package org.shixuan.inventory.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购单明细实体类
 */
@Data
public class PurchaseOrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 明细ID
     */
    private Long id;

    /**
     * 采购单ID
     */
    private Long purchaseOrderId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 采购数量
     */
    private Integer quantity;

    /**
     * 采购单价
     */
    private BigDecimal purchasePrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    // 关联信息
    /**
     * 商品名称 (非数据库字段)
     */
    private String productName;
    
    /**
     * 商品编码 (非数据库字段)
     */
    private String productCode;
    
    /**
     * 规格型号 (非数据库字段)
     */
    private String spec;
    
    /**
     * 单位 (非数据库字段)
     */
    private String unit;
} 