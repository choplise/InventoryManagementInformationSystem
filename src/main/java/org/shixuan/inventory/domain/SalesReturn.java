package org.shixuan.inventory.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售退货单实体类
 */
@Data
public class SalesReturn implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 销售退货单ID
     */
    private Long id;

    /**
     * 销售退货单号
     */
    private String returnNo;

    /**
     * 原销售单ID
     */
    private Long salesOrderId;
    
    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 退货日期
     */
    private Date returnDate;

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
     * 客户名称 (非数据库字段)
     */
    private String customerName;

    /**
     * 创建人名称 (非数据库字段)
     */
    private String creatorName;

    /**
     * 销售退货单明细列表 (非数据库字段)
     */
    private List<SalesReturnItem> items;
} 