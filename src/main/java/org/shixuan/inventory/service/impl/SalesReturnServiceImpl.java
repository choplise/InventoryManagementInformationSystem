package org.shixuan.inventory.service.impl;

import org.shixuan.inventory.domain.Inventory;
import org.shixuan.inventory.domain.InventoryRecord;
import org.shixuan.inventory.domain.SalesReturn;
import org.shixuan.inventory.domain.SalesReturnItem;
import org.shixuan.inventory.dto.SalesReturnDTO;
import org.shixuan.inventory.mapper.InventoryMapper;
import org.shixuan.inventory.mapper.InventoryRecordMapper;
import org.shixuan.inventory.mapper.SalesReturnMapper;
import org.shixuan.inventory.service.SalesReturnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SalesReturnServiceImpl implements SalesReturnService {

    @Autowired
    private SalesReturnMapper salesReturnMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private InventoryRecordMapper inventoryRecordMapper;


    @Override
    public List<SalesReturn> listSalesReturns(String returnNo, Long customerId, Integer status, Date startDate, Date endDate) {
        return salesReturnMapper.selectList(returnNo, customerId, status, startDate, endDate);
    }

    @Override
    public SalesReturn getSalesReturnById(Long id) {
        SalesReturn salesReturn = salesReturnMapper.selectById(id);
        if (salesReturn != null) {
            salesReturn.setItems(salesReturnMapper.selectItemsByReturnId(id));
        }
        return salesReturn;
    }

    @Override
    @Transactional
    public void createSalesReturn(SalesReturnDTO salesReturnDTO) {
        SalesReturn salesReturn = new SalesReturn();
        BeanUtils.copyProperties(salesReturnDTO, salesReturn);
        salesReturn.setReturnDate(new Date());
        salesReturn.setStatus(0); // 待审核
        salesReturnMapper.insert(salesReturn);

        for (SalesReturnItem item : salesReturnDTO.getItems()) {
            item.setSalesReturnId(salesReturn.getId());
        }
        salesReturnMapper.batchInsertItems(salesReturnDTO.getItems());
    }

    @Override
    @Transactional
    public void updateSalesReturn(SalesReturnDTO salesReturnDTO) {
        SalesReturn salesReturn = new SalesReturn();
        BeanUtils.copyProperties(salesReturnDTO, salesReturn);
        salesReturnMapper.update(salesReturn);

        salesReturnMapper.deleteItemsByReturnId(salesReturn.getId());
        for (SalesReturnItem item : salesReturnDTO.getItems()) {
            item.setSalesReturnId(salesReturn.getId());
        }
        salesReturnMapper.batchInsertItems(salesReturnDTO.getItems());
    }

    @Override
    @Transactional
    public void deleteSalesReturn(Long id) {
        salesReturnMapper.deleteItemsByReturnId(id);
        salesReturnMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void auditSalesReturn(Long id) {
        SalesReturn salesReturn = salesReturnMapper.selectById(id);
        if (salesReturn != null && salesReturn.getStatus() == 0) {
            salesReturnMapper.updateStatus(id, 1); // 已审核

            // 更新库存
            List<SalesReturnItem> items = salesReturnMapper.selectItemsByReturnId(id);
            for (SalesReturnItem item : items) {
                Inventory inventory = inventoryMapper.selectByProductId(item.getProductId());
                if (inventory != null) {
                    Integer new_quantity = inventory.getQuantity() + item.getQuantity();
                    inventoryMapper.updateQuantity(item.getProductId(), new_quantity);
                } else {
                    inventory = new Inventory();
                    inventory.setProductId(item.getProductId());
                    inventory.setQuantity( item.getQuantity());
                    inventoryMapper.insert(inventory);
                }

                // 增加库存记录
                InventoryRecord record = new InventoryRecord();
                record.setProductId(item.getProductId());
                record.setChangeQuantity(item.getQuantity());
                record.setQuantityAfterChange(inventory.getQuantity());
                record.setChangeType(4);
                record.setRelatedOrderNo(salesReturn.getReturnNo());
                inventoryRecordMapper.insert(record);
            }
        }
    }
} 