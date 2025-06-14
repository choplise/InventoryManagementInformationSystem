package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.SalesOrder;
import org.shixuan.inventory.domain.SalesOrderItem;

import java.util.Date;
import java.util.List;

/**
 * 销售单Mapper接口
 */
public interface SalesOrderMapper {

    /**
     * 查询销售单列表
     */
    List<SalesOrder> selectList(@Param("orderNo") String orderNo,
                                @Param("customerId") Long customerId,
                                @Param("status") Integer status,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);

    /**
     * 通过ID查询销售单
     */
    SalesOrder selectById(Long id);

    /**
     * 新增销售单
     */
    int insert(SalesOrder salesOrder);

    /**
     * 更新销售单
     */
    int update(SalesOrder salesOrder);

    /**
     * 更新销售单状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询销售单明细列表
     */
    List<SalesOrderItem> selectItemsByOrderId(Long salesOrderId);

    /**
     * 批量新增销售单明细
     */
    int batchInsertItems(@Param("items") List<SalesOrderItem> items);

    /**
     * 根据销售单ID删除明细
     */
    int deleteItemsByOrderId(Long salesOrderId);
} 