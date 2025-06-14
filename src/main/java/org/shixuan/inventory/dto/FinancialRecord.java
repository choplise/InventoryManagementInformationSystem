package org.shixuan.inventory.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务记录DTO
 */
@Data
public class FinancialRecord {
    /**
     * 记录类型 (e.g., "采购支出", "销售收入")
     */
    private String type;

    /**
     * 关联单号
     */
    private String orderNo;

    /**
     * 发生日期
     */
    private Date date;

    /**
     * 金额 (收入为正, 支出为负)
     */
    private BigDecimal amount;

    /**
     * 关联方 (供应商或客户)
     */
    private String party;

    /**
     * 经办人
     */
    private String operator;
} 