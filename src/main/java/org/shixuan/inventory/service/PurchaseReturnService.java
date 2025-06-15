package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.PurchaseReturn;
import org.shixuan.inventory.dto.PurchaseReturnDTO;

import java.util.Date;
import java.util.List;

public interface PurchaseReturnService {
    List<PurchaseReturn> listPurchaseReturns(String returnNo, Long supplierId, Integer status, Date startDate, Date endDate);

    PurchaseReturn getPurchaseReturnById(Long id);

    void createPurchaseReturn(PurchaseReturnDTO purchaseReturnDTO);

    void updatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO);

    void deletePurchaseReturn(Long id);

    void auditPurchaseReturn(Long id);
} 