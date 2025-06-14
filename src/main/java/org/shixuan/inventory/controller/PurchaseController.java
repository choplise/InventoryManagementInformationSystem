package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.PurchaseOrder;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.enums.ResultCode;
import org.shixuan.inventory.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 采购管理API
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 分页查询采购单
     */
    @GetMapping("/page")
    public Result<PageResult<PurchaseOrder>> queryPurchaseOrders(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        PageResult<PurchaseOrder> pageResult = purchaseService.queryPurchaseOrders(pageNum, pageSize, orderNo, supplierId, status, startDate, endDate);
        return Result.success(pageResult);
    }

    /**
     * 创建采购单
     */
    @PostMapping
    public Result<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        // TODO: 从token中获取当前用户ID，并设置为采购员ID
        // purchaseOrder.setPurchaserId(currentUserId);
        PurchaseOrder createdOrder = purchaseService.createPurchaseOrder(purchaseOrder);
        return Result.success(createdOrder);
    }

    /**
     * 获取采购单详情
     */
    @GetMapping("/{id}")
    public Result<PurchaseOrder> getPurchaseOrderDetails(@PathVariable Long id) {
        PurchaseOrder order = purchaseService.getPurchaseOrderDetails(id);
        if (order == null) {
            return Result.failed(ResultCode.PURCHASE_ORDER_NOT_EXIST);
        }
        return Result.success(order);
    }

    /**
     * 更新采购单
     */
    @PutMapping("/{id}")
    public Result<PurchaseOrder> updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder) {
        purchaseOrder.setId(id);
        PurchaseOrder updatedOrder = purchaseService.updatePurchaseOrder(purchaseOrder);
        return Result.success(updatedOrder);
    }

    /**
     * 审核采购单
     */
    @PostMapping("/{id}/approve")
    public Result<Void> approvePurchaseOrder(@PathVariable Long id) {
        boolean success = purchaseService.approvePurchaseOrder(id);
        return success ? Result.success() : Result.failed("审核失败");
    }

    /**
     * 执行采购入库
     */
    @PostMapping("/{id}/stock-in")
    public Result<Void> executeStockIn(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        Long operatorId = payload.get("operatorId"); // 前端应传来操作员ID，或从token中解析
        boolean success = purchaseService.executePurchaseStockIn(id, operatorId);
        return success ? Result.success() : Result.failed("入库失败");
    }

    /**
     * 作废采购单
     */
    @PostMapping("/{id}/cancel")
    public Result<Void> cancelPurchaseOrder(@PathVariable Long id) {
        boolean success = purchaseService.cancelPurchaseOrder(id);
        return success ? Result.success() : Result.failed("作废失败");
    }
} 