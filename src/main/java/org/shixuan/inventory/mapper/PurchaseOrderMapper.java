package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.PurchaseOrder;
import org.shixuan.inventory.domain.PurchaseOrderItem;

import java.util.Date;
import java.util.List;

/**
 * 采购单Mapper接口
 */
public interface PurchaseOrderMapper {

    /**
     * 查询采购单列表
     *
     * @param orderNo 采购单号
     * @param supplierId 供应商ID
     * @param status 状态
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 采购单列表
     */
    List<PurchaseOrder> selectList(@Param("orderNo") String orderNo,
                                   @Param("supplierId") Long supplierId,
                                   @Param("status") Integer status,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate);

    /**
     * 通过ID查询采购单
     *
     * @param id 采购单ID
     * @return 采购单信息
     */
    PurchaseOrder selectById(Long id);

    /**
     * 新增采购单
     *
     * @param purchaseOrder 采购单信息
     * @return 影响行数
     */
    int insert(PurchaseOrder purchaseOrder);

    /**
     * 更新采购单
     *
     * @param purchaseOrder 采购单信息
     * @return 影响行数
     */
    int update(PurchaseOrder purchaseOrder);
    
    /**
     * 更新采购单状态
     *
     * @param id 采购单ID
     * @param status 状态
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);


    /**
     * 查询采购单明细列表
     *
     * @param purchaseOrderId 采购单ID
     * @return 采购单明细列表
     */
    List<PurchaseOrderItem> selectItemsByOrderId(Long purchaseOrderId);

    /**
     * 批量新增采购单明细
     *
     * @param items 采购单明细列表
     * @return 影响行数
     */
    int batchInsertItems(@Param("items") List<PurchaseOrderItem> items);

    /**
     * 根据采购单ID删除明细
     *
     * @param purchaseOrderId 采购单ID
     * @return 影响行数
     */
    int deleteItemsByOrderId(Long purchaseOrderId);
} 