package org.shixuan.inventory.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.SalesOrder;
import org.shixuan.inventory.domain.SalesOrderItem;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.SalesOrderMapper;
import org.shixuan.inventory.service.InventoryService;
import org.shixuan.inventory.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售服务实现类
 */
@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public PageResult<SalesOrder> querySalesOrders(int pageNum, int pageSize, String orderNo, Long customerId, Integer status, Date startDate, Date endDate) {
        PageHelper.startPage(pageNum, pageSize);
        List<SalesOrder> list = salesOrderMapper.selectList(orderNo, customerId, status, startDate, endDate);
        PageInfo<SalesOrder> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesOrder createSalesOrder(SalesOrder salesOrder) {
        if (CollectionUtils.isEmpty(salesOrder.getItems())) {
            throw new ServiceException("销售单明细不能为空");
        }
        calculateAmounts(salesOrder);
        salesOrder.setStatus(0); // 待审核
        salesOrder.setOrderNo("SO" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));
        salesOrderMapper.insert(salesOrder);
        Long orderId = salesOrder.getId();

        for (SalesOrderItem item : salesOrder.getItems()) {
            item.setSalesOrderId(orderId);
        }
        salesOrderMapper.batchInsertItems(salesOrder.getItems());
        return salesOrder;
    }

    @Override
    public SalesOrder getSalesOrderDetails(Long id) {
        SalesOrder order = salesOrderMapper.selectById(id);
        if (order != null) {
            order.setItems(salesOrderMapper.selectItemsByOrderId(id));
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesOrder updateSalesOrder(SalesOrder salesOrder) {
        SalesOrder existing = salesOrderMapper.selectById(salesOrder.getId());
        if (existing == null) {
            throw new ServiceException("销售单不存在");
        }
        if (existing.getStatus() != 0) {
            throw new ServiceException("只有待审核状态的销售单才能修改");
        }
        if (CollectionUtils.isEmpty(salesOrder.getItems())) {
            throw new ServiceException("销售单明细不能为空");
        }
        calculateAmounts(salesOrder);
        salesOrderMapper.update(salesOrder);
        salesOrderMapper.deleteItemsByOrderId(salesOrder.getId());
        for (SalesOrderItem item : salesOrder.getItems()) {
            item.setSalesOrderId(salesOrder.getId());
        }
        salesOrderMapper.batchInsertItems(salesOrder.getItems());
        return salesOrder;
    }

    @Override
    public boolean approveSalesOrder(Long id) {
        SalesOrder existing = salesOrderMapper.selectById(id);
        if (existing == null) {
            throw new ServiceException("销售单不存在");
        }
        if (existing.getStatus() != 0) {
            throw new ServiceException("只有待审核状态的销售单才能审核");
        }
        return salesOrderMapper.updateStatus(id, 1) > 0; // 1: 已审核
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean executeSalesStockOut(Long id, Long operatorId) {
        SalesOrder order = getSalesOrderDetails(id);
        if (order == null) {
            throw new ServiceException("销售单不存在");
        }
        if (order.getStatus() != 1) {
            throw new ServiceException("只有已审核状态的销售单才能出库");
        }
        // 检查并扣减库存
        for (SalesOrderItem item : order.getItems()) {
            inventoryService.decreaseInventory(item.getProductId(), item.getQuantity(), 2, order.getOrderNo(), operatorId);
        }
        return salesOrderMapper.updateStatus(id, 2) > 0; // 2: 已出库
    }

    @Override
    public boolean cancelSalesOrder(Long id) {
        SalesOrder existing = salesOrderMapper.selectById(id);
        if (existing == null) {
            throw new ServiceException("销售单不存在");
        }
        if (existing.getStatus() == 2 || existing.getStatus() == -1) {
            throw new ServiceException("当前状态不能作废");
        }
        // 作废的订单需要把预扣的库存还回去，这里简化处理，假设未出库的订单不影响实际库存
        return salesOrderMapper.updateStatus(id, -1) > 0;
    }

    private void calculateAmounts(SalesOrder salesOrder) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (SalesOrderItem item : salesOrder.getItems()) {
            if (item.getQuantity() == null || item.getSalePrice() == null) {
                throw new ServiceException("明细的数量和单价不能为空");
            }
            BigDecimal amount = item.getSalePrice().multiply(new BigDecimal(item.getQuantity()));
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount);
        }
        salesOrder.setTotalAmount(totalAmount);
    }
} 