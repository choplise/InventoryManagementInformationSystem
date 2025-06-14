package org.shixuan.inventory.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 */
@Setter
@Getter
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 商品ID
     */
    private Long id;
    
    /**
     * 商品编码
     */
    private String productCode;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 商品分类ID
     */
    private Long categoryId;
    
    /**
     * 规格型号
     */
    private String spec;
    
    /**
     * 单位
     */
    private String unit;
    
    /**
     * 最新进价
     */
    private BigDecimal purchasePrice;
    
    /**
     * 销售价
     */
    private BigDecimal salePrice;
    
    /**
     * 库存下限
     */
    private Integer lowerLimit;
    
    /**
     * 库存上限
     */
    private Integer upperLimit;
    
    /**
     * 状态 (1:在售, 0:停产)
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
    
    // 非数据库字段
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 当前库存数量
     */
    private Integer stockQuantity;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", spec='" + spec + '\'' +
                ", unit='" + unit + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", salePrice=" + salePrice +
                ", status=" + status +
                '}';
    }
} 