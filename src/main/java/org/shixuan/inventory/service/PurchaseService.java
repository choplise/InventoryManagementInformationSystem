package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.PurchaseOrder;
import org.shixuan.inventory.dto.PageResult;

import java.util.Date;

/**
 * 采购服务接口
 */
public interface PurchaseService {

    /**
     * 分页查询采购单
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param orderNo 采购单号
     * @param supplierId 供应商ID
     * @param status 状态
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    PageResult<PurchaseOrder> queryPurchaseOrders(int pageNum, int pageSize, String orderNo, Long supplierId, Integer status, Date startDate, Date endDate);

    /**
     * 创建采购单
     * @param purchaseOrder
     * @return
     */
    PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 获取采购单详情
     * @param id
     * @return
     */
    PurchaseOrder getPurchaseOrderDetails(Long id);

    /**
     * 更新采购单
     * @param purchaseOrder
     * @return
     */
    PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 审核采购单
     * @param id
     * @return
     */
    boolean approvePurchaseOrder(Long id);

    /**
     * 执行采购入库
     * @param id
     * @param operatorId
     * @return
     */
    boolean executePurchaseStockIn(Long id, Long operatorId);

    /**
     * 作废采购单
     * @param id
     * @return
     */
    boolean cancelPurchaseOrder(Long id);
} 