package org.shixuan.inventory.service.impl;

import org.shixuan.inventory.domain.PurchaseOrder;
import org.shixuan.inventory.domain.SalesOrder;
import org.shixuan.inventory.dto.FinancialRecord;
import org.shixuan.inventory.mapper.PurchaseOrderMapper;
import org.shixuan.inventory.mapper.SalesOrderMapper;
import org.shixuan.inventory.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务服务实现类
 */
@Service
public class FinancialServiceImpl implements FinancialService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Override
    public List<FinancialRecord> getFinancialRecords(Date startDate, Date endDate) {
        List<FinancialRecord> records = new ArrayList<>();

        // 1. 查询已入库的采购单 (状态为2)
        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectList(null, null, 2, startDate, endDate);
        for (PurchaseOrder po : purchaseOrders) {
            FinancialRecord record = new FinancialRecord();
            record.setType("采购支出");
            record.setOrderNo(po.getOrderNo());
            record.setDate(po.getPurchaseDate());
            record.setAmount(po.getTotalAmount().negate()); // 支出为负数
            record.setParty(po.getSupplierName());
            record.setOperator(po.getPurchaserName());
            records.add(record);
        }

        // 2. 查询已出库的销售单 (状态为2)
        List<SalesOrder> salesOrders = salesOrderMapper.selectList(null, null, 2, startDate, endDate);
        for (SalesOrder so : salesOrders) {
            FinancialRecord record = new FinancialRecord();
            record.setType("销售收入");
            record.setOrderNo(so.getOrderNo());
            record.setDate(so.getSalesDate());
            record.setAmount(so.getTotalAmount()); // 收入为正数
            record.setParty(so.getCustomerName());
            record.setOperator(so.getSellerName());
            records.add(record);
        }

        // 3. 按日期排序
        return records.stream()
                .sorted(Comparator.comparing(FinancialRecord::getDate).reversed())
                .collect(Collectors.toList());
    }
} 