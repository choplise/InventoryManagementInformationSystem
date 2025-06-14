package org.shixuan.inventory.controller;

import org.shixuan.inventory.dto.FinancialRecord;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 财务管理API
 */
@RestController
@RequestMapping("/financial")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    /**
     * 查询财务流水
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @PreAuthorize("hasAuthority('financial:records')")
    @GetMapping("/records")
    public Result<List<FinancialRecord>> getFinancialRecords(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<FinancialRecord> records = financialService.getFinancialRecords(startDate, endDate);
        return Result.success(records);
    }
} 