package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.SalesReturn;
import org.shixuan.inventory.dto.SalesReturnDTO;

import java.util.Date;
import java.util.List;

public interface SalesReturnService {
    List<SalesReturn> listSalesReturns(String returnNo, Long customerId, Integer status, Date startDate, Date endDate);

    SalesReturn getSalesReturnById(Long id);

    void createSalesReturn(SalesReturnDTO salesReturnDTO);

    void updateSalesReturn(SalesReturnDTO salesReturnDTO);

    void deleteSalesReturn(Long id);

    void auditSalesReturn(Long id);
} 