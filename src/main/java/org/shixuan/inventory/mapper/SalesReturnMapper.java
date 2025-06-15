package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.SalesReturn;
import org.shixuan.inventory.domain.SalesReturnItem;

import java.util.Date;
import java.util.List;

/**
 * 销售退货单Mapper接口
 */
public interface SalesReturnMapper {

    /**
     * 查询销售退货单列表
     */
    List<SalesReturn> selectList(@Param("returnNo") String returnNo,
                                 @Param("customerId") Long customerId,
                                 @Param("status") Integer status,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);

    /**
     * 通过ID查询销售退货单
     */
    SalesReturn selectById(Long id);

    /**
     * 新增销售退货单
     */
    int insert(SalesReturn salesReturn);

    /**
     * 更新销售退货单
     */
    int update(SalesReturn salesReturn);

    /**
     * 更新销售退货单状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询销售退货单明细列表
     */
    List<SalesReturnItem> selectItemsByReturnId(Long salesReturnId);

    /**
     * 批量新增销售退货单明细
     */
    int batchInsertItems(@Param("items") List<SalesReturnItem> items);

    /**
     * 根据销售退货单ID删除明细
     */
    int deleteItemsByReturnId(Long salesReturnId);

    /**
     * 根据ID删除销售退货单
     */
    int deleteById(Long id);
} 