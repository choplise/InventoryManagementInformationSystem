package org.shixuan.inventory.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售单实体类
 */
@Data
public class SalesOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 销售单ID
     */
    private Long id;

    /**
     * 销售单号
     */
    private String orderNo;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 销售员ID
     */
    private Long sellerId;

    /**
     * 销售日期
     */
    private Date salesDate;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 状态 (0:待审核, 1:已审核, 2:已出库, -1:已作废)
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
     * 客户名称 (非数据库字段)
     */
    private String customerName;

    /**
     * 销售员名称 (非数据库字段)
     */
    private String sellerName;

    /**
     * 销售单明细列表 (非数据库字段)
     */
    private List<SalesOrderItem> items;
} 