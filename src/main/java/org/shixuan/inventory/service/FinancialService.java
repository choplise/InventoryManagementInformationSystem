package org.shixuan.inventory.service;

import org.shixuan.inventory.dto.FinancialRecord;

import java.util.Date;
import java.util.List;

/**
 * 财务服务接口
 */
public interface FinancialService {

    /**
     * 查询财务流水记录
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 财务记录列表
     */
    List<FinancialRecord> getFinancialRecords(Date startDate, Date endDate);
} 