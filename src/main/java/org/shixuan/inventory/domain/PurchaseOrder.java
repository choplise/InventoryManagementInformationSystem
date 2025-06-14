package org.shixuan.inventory.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购单实体类
 */
@Data
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 采购单ID
     */
    private Long id;

    /**
     * 采购单号
     */
    private String orderNo;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 采购员ID
     */
    private Long purchaserId;

    /**
     * 采购日期
     */
    private Date purchaseDate;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 状态 (0:待审核, 1:已审核, 2:已入库, -1:已作废)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    
    // 关联信息
    /**
     * 供应商名称 (非数据库字段)
     */
    private String supplierName;
    
    /**
     * 采购员名称 (非数据库字段)
     */
    private String purchaserName;

    /**
     * 采购单明细列表 (非数据库字段)
     */
    private List<PurchaseOrderItem> items;
} 