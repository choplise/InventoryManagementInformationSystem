package org.shixuan.inventory.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存变动记录实体类
 */
@Setter
@Getter
public class InventoryRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 商品ID
     */
    private Long productId;
    
    /**
     * 变动数量 (正数表示入库, 负数表示出库)
     */
    private Integer changeQuantity;
    
    /**
     * 变动后数量
     */
    private Integer quantityAfterChange;
    
    /**
     * 变动类型 (1:采购入库, 2:销售出库, 3:采购退货, 4:销售退货, 5:库存盘点)
     */
    private Integer changeType;
    
    /**
     * 关联单据号
     */
    private String relatedOrderNo;
    
    /**
     * 操作人ID
     */
    private Long operatorId;
    
    /**
     * 操作时间
     */
    private Date operateTime;
    
    // 非数据库字段
    /**
     * 商品编码
     */
    private String productCode;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 操作人用户名
     */
    private String operatorName;

    @Override
    public String toString() {
        return "InventoryRecord{" +
                "id=" + id +
                ", productId=" + productId +
                ", changeQuantity=" + changeQuantity +
                ", quantityAfterChange=" + quantityAfterChange +
                ", changeType=" + changeType +
                ", relatedOrderNo='" + relatedOrderNo + '\'' +
                '}';
    }
} 