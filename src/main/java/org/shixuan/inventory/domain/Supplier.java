package org.shixuan.inventory.domain;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 供应商实体类
 */
@Data
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    private Long id;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 (1:合作中, 0:停止合作)
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
} 