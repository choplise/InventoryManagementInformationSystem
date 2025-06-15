package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.PurchaseReturn;
import org.shixuan.inventory.domain.PurchaseReturnItem;

import java.util.Date;
import java.util.List;

/**
 * 采购退货单Mapper接口
 */
public interface PurchaseReturnMapper {

    /**
     * 查询采购退货单列表
     */
    List<PurchaseReturn> selectList(@Param("returnNo") String returnNo,
                                    @Param("supplierId") Long supplierId,
                                    @Param("status") Integer status,
                                    @Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate);

    /**
     * 通过ID查询采购退货单
     */
    PurchaseReturn selectById(Long id);

    /**
     * 新增采购退货单
     */
    int insert(PurchaseReturn purchaseReturn);

    /**
     * 更新采购退货单
     */
    int update(PurchaseReturn purchaseReturn);

    /**
     * 更新采购退货单状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询采购退货单明细列表
     */
    List<PurchaseReturnItem> selectItemsByReturnId(Long purchaseReturnId);

    /**
     * 批量新增采购退货单明细
     */
    int batchInsertItems(@Param("items") List<PurchaseReturnItem> items);

    /**
     * 根据采购退货单ID删除明细
     */
    int deleteItemsByReturnId(Long purchaseReturnId);

    /**
     * 根据ID删除采购退货单
     */
    int deleteById(Long id);
} 