package org.shixuan.inventory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.Supplier;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.SupplierMapper;
import org.shixuan.inventory.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 供应商服务实现类
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public PageResult<Supplier> getSupplierPage(int pageNum, int pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Supplier> list = supplierMapper.selectList(keyword);
        PageInfo<Supplier> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
    
    @Override
    public List<Supplier> listAll() {
        return supplierMapper.selectList(null);
    }

    @Override
    public Supplier getSupplierById(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public Long addSupplier(Supplier supplier) {
        // 检查供应商编码是否已存在
        Supplier existing = supplierMapper.selectByCode(supplier.getSupplierCode());
        if (existing != null) {
            throw new ServiceException("供应商编码已存在");
        }

        // 设置默认值
        if (supplier.getStatus() == null) {
            supplier.setStatus(1); // 默认合作中
        }

        int rows = supplierMapper.insert(supplier);
        if (rows > 0) {
            return supplier.getId();
        }
        throw new ServiceException("添加供应商失败");
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        Supplier existing = supplierMapper.selectById(supplier.getId());
        if (existing == null) {
            throw new ServiceException("供应商不存在");
        }
        
        //如果修改了编码, 检查新编码是否已存在
        if (supplier.getSupplierCode() != null && !supplier.getSupplierCode().equals(existing.getSupplierCode())) {
            Supplier sameCode = supplierMapper.selectByCode(supplier.getSupplierCode());
            if (sameCode != null) {
                throw new ServiceException("供应商编码已存在");
            }
        }

        int rows = supplierMapper.update(supplier);
        return rows > 0;
    }

    @Override
    public boolean deleteSupplier(Long id) {
        // 检查供应商是否存在
        Supplier existing = supplierMapper.selectById(id);
        if (existing == null) {
            throw new ServiceException("供应商不存在");
        }

        // TODO: 检查供应商是否有关联的采购单等业务数据

        int rows = supplierMapper.deleteById(id);
        return rows > 0;
    }
} 