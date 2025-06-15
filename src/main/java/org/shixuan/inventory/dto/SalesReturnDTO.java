package org.shixuan.inventory.dto;

import lombok.Data;
import org.shixuan.inventory.domain.SalesReturnItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SalesReturnDTO {
    private Long id;
    private String returnNo;
    private Long salesOrderId;
    private Long customerId;
    private Long creatorId;
    private Date returnDate;
    private BigDecimal totalAmount;
    private Integer status;
    private String remark;
    private List<SalesReturnItem> items;
} 