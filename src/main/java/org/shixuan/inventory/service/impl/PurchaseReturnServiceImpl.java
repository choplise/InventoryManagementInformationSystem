package org.shixuan.inventory.service.impl;

import org.shixuan.inventory.domain.Inventory;
import org.shixuan.inventory.domain.InventoryRecord;
import org.shixuan.inventory.domain.PurchaseReturn;
import org.shixuan.inventory.domain.PurchaseReturnItem;
import org.shixuan.inventory.dto.PurchaseReturnDTO;
import org.shixuan.inventory.mapper.InventoryMapper;
import org.shixuan.inventory.mapper.InventoryRecordMapper;
import org.shixuan.inventory.mapper.PurchaseReturnMapper;
import org.shixuan.inventory.service.PurchaseReturnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

    @Autowired
    private PurchaseReturnMapper purchaseReturnMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private InventoryRecordMapper inventoryRecordMapper;

    @Override
    public List<PurchaseReturn> listPurchaseReturns(String returnNo, Long supplierId, Integer status, Date startDate, Date endDate) {
        return purchaseReturnMapper.selectList(returnNo, supplierId, status, startDate, endDate);
    }

    @Override
    public PurchaseReturn getPurchaseReturnById(Long id) {
        PurchaseReturn purchaseReturn = purchaseReturnMapper.selectById(id);
        if (purchaseReturn != null) {
            purchaseReturn.setItems(purchaseReturnMapper.selectItemsByReturnId(id));
        }
        return purchaseReturn;
    }

    @Override
    @Transactional
    public void createPurchaseReturn(PurchaseReturnDTO purchaseReturnDTO) {
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        BeanUtils.copyProperties(purchaseReturnDTO, purchaseReturn);
        purchaseReturn.setReturnDate(new Date());
        purchaseReturn.setStatus(0); // 待审核
        purchaseReturnMapper.insert(purchaseReturn);

        for (PurchaseReturnItem item : purchaseReturnDTO.getItems()) {
            item.setPurchaseReturnId(purchaseReturn.getId());
        }
        purchaseReturnMapper.batchInsertItems(purchaseReturnDTO.getItems());
    }

    @Override
    @Transactional
    public void updatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO) {
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        BeanUtils.copyProperties(purchaseReturnDTO, purchaseReturn);
        purchaseReturnMapper.update(purchaseReturn);

        purchaseReturnMapper.deleteItemsByReturnId(purchaseReturn.getId());
        for (PurchaseReturnItem item : purchaseReturnDTO.getItems()) {
            item.setPurchaseReturnId(purchaseReturn.getId());
        }
        purchaseReturnMapper.batchInsertItems(purchaseReturnDTO.getItems());
    }

    @Override
    @Transactional
    public void deletePurchaseReturn(Long id) {
        purchaseReturnMapper.deleteItemsByReturnId(id);
        purchaseReturnMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void auditPurchaseReturn(Long id) {
        PurchaseReturn purchaseReturn = purchaseReturnMapper.selectById(id);
        if (purchaseReturn != null && purchaseReturn.getStatus() == 0) {
            purchaseReturnMapper.updateStatus(id, 1); // 已审核

            // 更新库存
            List<PurchaseReturnItem> items = purchaseReturnMapper.selectItemsByReturnId(id);
            for (PurchaseReturnItem item : items) {
                Inventory inventory = inventoryMapper.selectByProductId(item.getProductId());
                if (inventory != null) {
                    Integer new_quantity=inventory.getQuantity() - item.getQuantity();
                    inventoryMapper.updateQuantity(item.getProductId(),new_quantity);
                }
                else {
                    // 如果库存不存在，则创建新的库存记录
                    inventory = new Inventory();
                    inventory.setProductId(item.getProductId());
                    inventory.setQuantity(-item.getQuantity());
                    inventoryMapper.insert(inventory);
                }

                // 增加库存记录
                InventoryRecord record = new InventoryRecord();
                record.setProductId(item.getProductId());
                record.setChangeQuantity(-item.getQuantity());
                record.setQuantityAfterChange(inventory.getQuantity());
                record.setChangeType(3);
                record.setRelatedOrderNo(purchaseReturn.getReturnNo());
                inventoryRecordMapper.insert(record);
            }
        }
    }
} 