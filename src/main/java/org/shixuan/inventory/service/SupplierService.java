package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.Supplier;
import org.shixuan.inventory.dto.PageResult;

import java.util.List;

/**
 * 供应商服务接口
 */
public interface SupplierService {

    /**
     * 分页查询供应商列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @param keyword  关键字
     * @return 分页供应商列表
     */
    PageResult<Supplier> getSupplierPage(int pageNum, int pageSize, String keyword);
    
    /**
     * 获取所有供应商
     * @return
     */
    List<Supplier> listAll();

    /**
     * 根据ID获取供应商
     *
     * @param id 供应商ID
     * @return 供应商信息
     */
    Supplier getSupplierById(Long id);

    /**
     * 添加供应商
     *
     * @param supplier 供应商信息
     * @return 新增的供应商ID
     */
    Long addSupplier(Supplier supplier);

    /**
     * 更新供应商
     *
     * @param supplier 供应商信息
     * @return 是否成功
     */
    boolean updateSupplier(Supplier supplier);

    /**
     * 删除供应商
     *
     * @param id 供应商ID
     * @return 是否成功
     */
    boolean deleteSupplier(Long id);
} 