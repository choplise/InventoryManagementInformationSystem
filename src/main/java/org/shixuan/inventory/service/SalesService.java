package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.SalesOrder;
import org.shixuan.inventory.dto.PageResult;

import java.util.Date;

/**
 * 销售服务接口
 */
public interface SalesService {

    /**
     * 分页查询销售单
     */
    PageResult<SalesOrder> querySalesOrders(int pageNum, int pageSize, String orderNo, Long customerId, Integer status, Date startDate, Date endDate);

    /**
     * 创建销售单
     */
    SalesOrder createSalesOrder(SalesOrder salesOrder);

    /**
     * 获取销售单详情
     */
    SalesOrder getSalesOrderDetails(Long id);

    /**
     * 更新销售单
     */
    SalesOrder updateSalesOrder(SalesOrder salesOrder);

    /**
     * 审核销售单
     */
    boolean approveSalesOrder(Long id);

    /**
     * 执行销售出库
     */
    boolean executeSalesStockOut(Long id, Long operatorId);

    /**
     * 作废销售单
     */
    boolean cancelSalesOrder(Long id);
} 