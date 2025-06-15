package org.shixuan.inventory.dto;

import lombok.Data;
import org.shixuan.inventory.domain.PurchaseReturnItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PurchaseReturnDTO {
    private Long id;
    private String returnNo;
    private Long purchaseOrderId;
    private Long supplierId;
    private Long creatorId;
    private Date returnDate;
    private BigDecimal totalAmount;
    private Integer status;
    private String remark;
    private List<PurchaseReturnItem> items;
} 