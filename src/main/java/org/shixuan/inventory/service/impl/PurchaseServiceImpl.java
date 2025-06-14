package org.shixuan.inventory.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.PurchaseOrder;
import org.shixuan.inventory.domain.PurchaseOrderItem;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.PurchaseOrderMapper;
import org.shixuan.inventory.service.InventoryService;
import org.shixuan.inventory.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购服务实现类
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public PageResult<PurchaseOrder> queryPurchaseOrders(int pageNum, int pageSize, String orderNo, Long supplierId, Integer status, Date startDate, Date endDate) {
        PageHelper.startPage(pageNum, pageSize);
        List<PurchaseOrder> list = purchaseOrderMapper.selectList(orderNo, supplierId, status, startDate, endDate);
        PageInfo<PurchaseOrder> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (CollectionUtils.isEmpty(purchaseOrder.getItems())) {
            throw new ServiceException("采购单明细不能为空");
        }

        // 1. 计算总金额和明细金额
        calculateAmounts(purchaseOrder);

        // 2. 设置订单状态和单号
        purchaseOrder.setStatus(0); // 待审核
        purchaseOrder.setOrderNo("PO" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));

        // 3. 插入主表
        purchaseOrderMapper.insert(purchaseOrder);
        Long orderId = purchaseOrder.getId();

        // 4. 插入明细表
        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            item.setPurchaseOrderId(orderId);
        }
        purchaseOrderMapper.batchInsertItems(purchaseOrder.getItems());

        return purchaseOrder;
    }

    @Override
    public PurchaseOrder getPurchaseOrderDetails(Long id) {
        PurchaseOrder order = purchaseOrderMapper.selectById(id);
        if (order != null) {
            List<PurchaseOrderItem> items = purchaseOrderMapper.selectItemsByOrderId(id);
            order.setItems(items);
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrder existingOrder = purchaseOrderMapper.selectById(purchaseOrder.getId());
        if (existingOrder == null) {
            throw new ServiceException("采购单不存在");
        }
        if (existingOrder.getStatus() != 0) { // 仅待审核的可以修改
            throw new ServiceException("只有待审核状态的采购单才能修改");
        }
        if (CollectionUtils.isEmpty(purchaseOrder.getItems())) {
            throw new ServiceException("采购单明细不能为空");
        }

        // 1. 重新计算总金额
        calculateAmounts(purchaseOrder);

        // 2. 更新主表
        purchaseOrderMapper.update(purchaseOrder);

        // 3. 删除旧明细，插入新明细
        purchaseOrderMapper.deleteItemsByOrderId(purchaseOrder.getId());
        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            item.setPurchaseOrderId(purchaseOrder.getId());
        }
        purchaseOrderMapper.batchInsertItems(purchaseOrder.getItems());

        return purchaseOrder;
    }

    @Override
    public boolean approvePurchaseOrder(Long id) {
        PurchaseOrder existingOrder = purchaseOrderMapper.selectById(id);
        if (existingOrder == null) {
            throw new ServiceException("采购单不存在");
        }
        if (existingOrder.getStatus() != 0) { // 仅待审核的可以审核
            throw new ServiceException("只有待审核状态的采购单才能审核");
        }
        return purchaseOrderMapper.updateStatus(id, 1) > 0; // 1: 已审核
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean executePurchaseStockIn(Long id, Long operatorId) {
        PurchaseOrder order = getPurchaseOrderDetails(id);
        if (order == null) {
            throw new ServiceException("采购单不存在");
        }
        if (order.getStatus() != 1) { // 仅已审核的可以入库
            throw new ServiceException("只有已审核状态的采购单才能入库");
        }

        // 更新库存
        for (PurchaseOrderItem item : order.getItems()) {
            inventoryService.increaseInventory(item.getProductId(), item.getQuantity(), 1, order.getOrderNo(), operatorId);
        }

        // 更新订单状态
        return purchaseOrderMapper.updateStatus(id, 2) > 0; // 2: 已入库
    }

    @Override
    public boolean cancelPurchaseOrder(Long id) {
        PurchaseOrder existingOrder = purchaseOrderMapper.selectById(id);
        if (existingOrder == null) {
            throw new ServiceException("采购单不存在");
        }
        if (existingOrder.getStatus() == 2 || existingOrder.getStatus() == -1) { // 已入库或已作废的不能作废
            throw new ServiceException("当前状态不能作废");
        }
        return purchaseOrderMapper.updateStatus(id, -1) > 0; // -1: 已作废
    }

    private void calculateAmounts(PurchaseOrder purchaseOrder) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            if (item.getQuantity() == null || item.getPurchasePrice() == null) {
                throw new ServiceException("明细的数量和单价不能为空");
            }
            BigDecimal amount = item.getPurchasePrice().multiply(new BigDecimal(item.getQuantity()));
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount);
        }
        purchaseOrder.setTotalAmount(totalAmount);
    }
} 