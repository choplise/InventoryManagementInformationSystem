package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.InventoryRecord;

import java.util.Date;
import java.util.List;

/**
 * 库存变动记录Mapper接口
 */
public interface InventoryRecordMapper {
    
    /**
     * 查询库存变动记录
     * 
     * @param productId 商品ID
     * @param changeType 变动类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 库存变动记录列表
     */
    List<InventoryRecord> selectList(
            @Param("productId") Long productId, 
            @Param("changeType") Integer changeType,
            @Param("startTime") Date startTime, 
            @Param("endTime") Date endTime);
    
    /**
     * 新增库存变动记录
     * 
     * @param record 库存变动记录
     * @return 影响行数
     */
    int insert(InventoryRecord record);
} 