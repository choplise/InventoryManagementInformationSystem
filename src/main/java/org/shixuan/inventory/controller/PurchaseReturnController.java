package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.PurchaseReturn;
import org.shixuan.inventory.dto.PurchaseReturnDTO;
import org.shixuan.inventory.service.PurchaseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/purchase-returns")
public class PurchaseReturnController {

    @Autowired
    private PurchaseReturnService purchaseReturnService;

    @GetMapping
    public List<PurchaseReturn> listPurchaseReturns(@RequestParam(required = false) String returnNo,
                                                     @RequestParam(required = false) Long supplierId,
                                                     @RequestParam(required = false) Integer status,
                                                     @RequestParam(required = false) Date startDate,
                                                     @RequestParam(required = false) Date endDate) {
        return purchaseReturnService.listPurchaseReturns(returnNo, supplierId, status, startDate, endDate);
    }

    @GetMapping("/{id}")
    public PurchaseReturn getPurchaseReturnById(@PathVariable Long id) {
        return purchaseReturnService.getPurchaseReturnById(id);
    }

    @PostMapping
    public void createPurchaseReturn(@RequestBody PurchaseReturnDTO purchaseReturnDTO) {
        purchaseReturnService.createPurchaseReturn(purchaseReturnDTO);
    }

    @PutMapping("/{id}")
    public void updatePurchaseReturn(@PathVariable Long id, @RequestBody PurchaseReturnDTO purchaseReturnDTO) {
        purchaseReturnDTO.setId(id);
        purchaseReturnService.updatePurchaseReturn(purchaseReturnDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePurchaseReturn(@PathVariable Long id) {
        purchaseReturnService.deletePurchaseReturn(id);
    }

    @PostMapping("/{id}/audit")
    public void auditPurchaseReturn(@PathVariable Long id) {
        purchaseReturnService.auditPurchaseReturn(id);
    }
} 