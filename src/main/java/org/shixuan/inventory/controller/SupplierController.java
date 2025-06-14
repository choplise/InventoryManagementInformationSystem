package org.shixuan.inventory.controller;

import org.shixuan.inventory.config.IgnoreAuth;
import org.shixuan.inventory.domain.Supplier;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.enums.ResultCode;
import org.shixuan.inventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商管理API
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 分页查询供应商列表
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @param keyword 关键字
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<PageResult<Supplier>> getSupplierPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword) {
        PageResult<Supplier> pageResult = supplierService.getSupplierPage(pageNum, pageSize, keyword);
        return Result.success(pageResult);
    }
    
    /**
     * 获取所有供应商
     * @return
     */
    @GetMapping("/list")
    public Result<List<Supplier>> listAll() {
        List<Supplier> suppliers = supplierService.listAll();
        return Result.success(suppliers);
    }


    /**
     * 根据ID获取供应商详情
     * @param id 供应商ID
     * @return 供应商信息
     */
    @GetMapping("/{id}")
    public Result<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierService.getSupplierById(id);
        if (supplier == null) {
            return Result.failed(ResultCode.SUPPLIER_NOT_EXIST);
        }
        return Result.success(supplier);
    }

    /**
     * 新增供应商
     * @param supplier 供应商信息
     * @return 操作结果
     */
    @PostMapping
    public Result<Long> addSupplier(@RequestBody Supplier supplier) {
        Long supplierId = supplierService.addSupplier(supplier);
        return Result.success(supplierId);
    }

    /**
     * 更新供应商
     * @param id 供应商ID
     * @param supplier 供应商信息
     * @return 操作结果
     */
    @PutMapping("/{id}")
    public Result<Void> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        boolean success = supplierService.updateSupplier(supplier);
        return success ? Result.success() : Result.failed("更新供应商失败");
    }

    /**
     * 删除供应商
     * @param id 供应商ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteSupplier(@PathVariable Long id) {
        boolean success = supplierService.deleteSupplier(id);
        return success ? Result.success() : Result.failed("删除供应商失败");
    }
} 