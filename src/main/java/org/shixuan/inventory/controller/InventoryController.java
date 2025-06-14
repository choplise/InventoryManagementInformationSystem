package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.Inventory;
import org.shixuan.inventory.domain.InventoryRecord;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.enums.ResultCode;
import org.shixuan.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存控制器
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);
    
    @Autowired
    private InventoryService inventoryService;
    
    /**
     * 分页查询库存列表
     */
    @PreAuthorize("hasAuthority('inventory:query')")
    @GetMapping("/page")
    public Result<PageResult<Inventory>> getInventoryPage(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "warningStatus", required = false) Integer warningStatus) {// 0: 不显示预警, 1: 显示低于下限, 2: 显示高于上限
        
        PageResult<Inventory> pageResult = inventoryService.getInventoryPage(pageNum, pageSize, keyword, warningStatus);
        return Result.success(pageResult);
    }
    
    /**
     * 根据商品ID获取库存
     */
    @GetMapping("/product/{productId}")
    public Result<Inventory> getInventoryByProductId(@PathVariable("productId") Long productId) {
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        if (inventory == null) {
            return Result.failed(ResultCode.INVENTORY_RECORD_NOT_FOUND);
        }
        return Result.success(inventory);
    }
    
    /**
     * 增加库存
     */
    @PostMapping("/increase")
    public Result<Boolean> increaseInventory(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("changeType") Integer changeType,
            @RequestParam("relatedOrderNo") String relatedOrderNo,
            @RequestParam("operatorId") Long operatorId) {
        
        boolean success = inventoryService.increaseInventory(productId, quantity, changeType, relatedOrderNo, operatorId);
        return Result.success(success);
    }
    
    /**
     * 减少库存
     */
    @PostMapping("/decrease")
    public Result<Boolean> decreaseInventory(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("changeType") Integer changeType,
            @RequestParam("relatedOrderNo") String relatedOrderNo,
            @RequestParam("operatorId") Long operatorId) {
        
        boolean success = inventoryService.decreaseInventory(productId, quantity, changeType, relatedOrderNo, operatorId);
        return Result.success(success);
    }
    
    /**
     * 分页查询库存变动记录
     */
    @GetMapping("/record/page")
    public Result<PageResult<InventoryRecord>> getInventoryRecordPage(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "changeType", required = false) Integer changeType,
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        
        PageResult<InventoryRecord> pageResult = inventoryService.getInventoryRecordPage(
                pageNum, pageSize, productId, changeType, startTime, endTime);
        return Result.success(pageResult);
    }
    
    /**
     * 检查库存是否充足
     */
    @GetMapping("/check")
    public Result<Boolean> checkInventory(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity) {
        
        boolean isEnough = inventoryService.checkInventory(productId, quantity);
        return Result.success(isEnough);
    }
    
    /**
     * 获取库存预警数据
     */
    @GetMapping("/warning")
    public Result<Map<String, Object>> getWarningInventory() {
        List<Inventory> lowerList = inventoryService.getWarningInventory(1); // 低于下限
        List<Inventory> upperList = inventoryService.getWarningInventory(2); // 高于上限
        
        Map<String, Object> result = new HashMap<>();
        result.put("lowerLimit", lowerList);
        result.put("upperLimit", upperList);
        
        return Result.success(result);
    }
} 