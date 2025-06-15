package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.SalesReturn;
import org.shixuan.inventory.dto.SalesReturnDTO;
import org.shixuan.inventory.service.SalesReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sales-returns")
public class SalesReturnController {

    @Autowired
    private SalesReturnService salesReturnService;

    @GetMapping
    public List<SalesReturn> listSalesReturns(@RequestParam(required = false) String returnNo,
                                              @RequestParam(required = false) Long customerId,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam(required = false) Date startDate,
                                              @RequestParam(required = false) Date endDate) {
        return salesReturnService.listSalesReturns(returnNo, customerId, status, startDate, endDate);
    }

    @GetMapping("/{id}")
    public SalesReturn getSalesReturnById(@PathVariable Long id) {
        return salesReturnService.getSalesReturnById(id);
    }

    @PostMapping
    public void createSalesReturn(@RequestBody SalesReturnDTO salesReturnDTO) {
        salesReturnService.createSalesReturn(salesReturnDTO);
    }

    @PutMapping("/{id}")
    public void updateSalesReturn(@PathVariable Long id, @RequestBody SalesReturnDTO salesReturnDTO) {
        salesReturnDTO.setId(id);
        salesReturnService.updateSalesReturn(salesReturnDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSalesReturn(@PathVariable Long id) {
        salesReturnService.deleteSalesReturn(id);
    }

    @PostMapping("/{id}/audit")
    public void auditSalesReturn(@PathVariable Long id) {
        salesReturnService.auditSalesReturn(id);
    }
} 