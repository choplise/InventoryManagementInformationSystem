package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.SalesOrder;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.enums.ResultCode;
import org.shixuan.inventory.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 销售管理API
 */
@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    /**
     * 分页查询销售单
     */
    @GetMapping("/page")
    public Result<PageResult<SalesOrder>> querySalesOrders(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        PageResult<SalesOrder> pageResult = salesService.querySalesOrders(pageNum, pageSize, orderNo, customerId, status, startDate, endDate);
        return Result.success(pageResult);
    }

    /**
     * 创建销售单
     */
    @PostMapping
    public Result<SalesOrder> createSalesOrder(@RequestBody SalesOrder salesOrder) {
        // TODO: 从token中获取当前用户ID，并设置为销售员ID
        SalesOrder createdOrder = salesService.createSalesOrder(salesOrder);
        return Result.success(createdOrder);
    }

    /**
     * 获取销售单详情
     */
    @GetMapping("/{id}")
    public Result<SalesOrder> getSalesOrderDetails(@PathVariable Long id) {
        SalesOrder order = salesService.getSalesOrderDetails(id);
        if (order == null) {
            return Result.failed(ResultCode.SALES_ORDER_NOT_EXIST);
        }
        return Result.success(order);
    }

    /**
     * 更新销售单
     */
    @PutMapping("/{id}")
    public Result<SalesOrder> updateSalesOrder(@PathVariable Long id, @RequestBody SalesOrder salesOrder) {
        salesOrder.setId(id);
        SalesOrder updatedOrder = salesService.updateSalesOrder(salesOrder);
        return Result.success(updatedOrder);
    }

    /**
     * 审核销售单
     */
    @PostMapping("/{id}/approve")
    public Result<Void> approveSalesOrder(@PathVariable Long id) {
        boolean success = salesService.approveSalesOrder(id);
        return success ? Result.success() : Result.failed("审核失败");
    }

    /**
     * 执行销售出库
     */
    @PostMapping("/{id}/stock-out")
    public Result<Void> executeStockOut(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        Long operatorId = payload.get("operatorId");
        boolean success = salesService.executeSalesStockOut(id, operatorId);
        return success ? Result.success() : Result.failed("出库失败");
    }

    /**
     * 作废销售单
     */
    @PostMapping("/{id}/cancel")
    public Result<Void> cancelSalesOrder(@PathVariable Long id) {
        boolean success = salesService.cancelSalesOrder(id);
        return success ? Result.success() : Result.failed("作废失败");
    }
} 