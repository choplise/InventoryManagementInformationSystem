package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.Inventory;

import java.util.List;

/**
 * 库存Mapper接口
 */
public interface InventoryMapper {
    
    /**
     * 查询库存列表
     * 
     * @param keyword 关键字(商品编码或名称)
     * @param warningStatus 预警状态 (0:正常, 1:低于下限, 2:高于上限)
     * @return 库存列表
     */
    List<Inventory> selectList(@Param("keyword") String keyword, @Param("warningStatus") Integer warningStatus);
    
    /**
     * 通过商品ID查询库存
     * 
     * @param productId 商品ID
     * @return 库存信息
     */
    Inventory selectByProductId(Long productId);
    
    /**
     * 插入库存记录
     * 
     * @param inventory 库存信息
     * @return 影响行数
     */
    int insert(Inventory inventory);
    
    /**
     * 更新库存数量
     * 
     * @param productId 商品ID
     * @param quantity 库存数量
     * @return 影响行数
     */
    int updateQuantity(@Param("productId") Long productId, @Param("quantity") Integer quantity);
    
    /**
     * 增加库存数量
     * 
     * @param productId 商品ID
     * @param quantity 增加的数量
     * @return 影响行数
     */
    int increaseQuantity(@Param("productId") Long productId, @Param("quantity") Integer quantity);
    
    /**
     * 减少库存数量
     * 
     * @param productId 商品ID
     * @param quantity 减少的数量
     * @return 影响行数
     */
    int decreaseQuantity(@Param("productId") Long productId, @Param("quantity") Integer quantity);
} 