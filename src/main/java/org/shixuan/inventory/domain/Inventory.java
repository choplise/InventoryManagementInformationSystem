package org.shixuan.inventory.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存实体类
 */
@Setter
@Getter
public class Inventory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 库存ID
     */
    private Long id;
    
    /**
     * 商品ID
     */
    private Long productId;
    
    /**
     * 库存数量
     */
    private Integer quantity;
    
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    
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
     * 商品单位
     */
    private String unit;
    
    /**
     * 库存下限
     */
    private Integer lowerLimit;
    
    /**
     * 库存上限
     */
    private Integer upperLimit;
    
    /**
     * 库存状态 (0:正常, 1:低于下限, 2:高于上限)
     */
    private Integer warningStatus;

    /**
     * 计算预警状态
     */
    public void calculateWarningStatus() {
        if (quantity != null && lowerLimit != null && upperLimit != null) {
            if (quantity < lowerLimit) {
                warningStatus = 1; // 低于下限
            } else if (quantity > upperLimit) {
                warningStatus = 2; // 高于上限
            } else {
                warningStatus = 0; // 正常
            }
        }
    }
    
    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", warningStatus=" + warningStatus +
                '}';
    }
} 