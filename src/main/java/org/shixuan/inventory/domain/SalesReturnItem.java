package org.shixuan.inventory.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售退货单明细实体类
 */
@Data
public class SalesReturnItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 明细ID
     */
    private Long id;

    /**
     * 销售退货单ID
     */
    private Long salesReturnId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 销售数量
     */
    private Integer quantity;

    /**
     * 销售单价
     */
    private BigDecimal salePrice;

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