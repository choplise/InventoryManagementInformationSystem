package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.Inventory;
import org.shixuan.inventory.domain.InventoryRecord;
import org.shixuan.inventory.dto.PageResult;

import java.util.Date;
import java.util.List;

/**
 * 库存服务接口
 */
public interface InventoryService {
    
    /**
     * 分页查询库存列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @param keyword 关键字
     * @param warningStatus 预警状态
     * @return 分页库存列表
     */
    PageResult<Inventory> getInventoryPage(int pageNum, int pageSize, String keyword, Integer warningStatus);
    
    /**
     * 根据商品ID获取库存
     * 
     * @param productId 商品ID
     * @return 库存信息
     */
    Inventory getInventoryByProductId(Long productId);
    
    /**
     * 增加库存
     * 
     * @param productId 商品ID
     * @param quantity 增加数量
     * @param changeType 变动类型
     * @param relatedOrderNo 关联单号
     * @param operatorId 操作人ID
     * @return 是否成功
     */
    boolean increaseInventory(Long productId, Integer quantity, Integer changeType, String relatedOrderNo, Long operatorId);
    
    /**
     * 减少库存
     * 
     * @param productId 商品ID
     * @param quantity 减少数量
     * @param changeType 变动类型
     * @param relatedOrderNo 关联单号
     * @param operatorId 操作人ID
     * @return 是否成功
     */
    boolean decreaseInventory(Long productId, Integer quantity, Integer changeType, String relatedOrderNo, Long operatorId);
    
    /**
     * 分页查询库存变动记录
     * 
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @param productId 商品ID
     * @param changeType 变动类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页库存变动记录
     */
    PageResult<InventoryRecord> getInventoryRecordPage(
            int pageNum, int pageSize, Long productId, Integer changeType, Date startTime, Date endTime);
    
    /**
     * 检查商品库存是否充足
     * 
     * @param productId 商品ID
     * @param quantity 数量
     * @return 是否充足
     */
    boolean checkInventory(Long productId, Integer quantity);
    
    /**
     * 获取库存预警商品列表
     * 
     * @param warningStatus 预警状态 (1:低于下限, 2:高于上限)
     * @return 预警商品列表
     */
    List<Inventory> getWarningInventory(Integer warningStatus);
} 