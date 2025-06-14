package org.shixuan.inventory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.Inventory;
import org.shixuan.inventory.domain.Product;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.InventoryMapper;
import org.shixuan.inventory.mapper.ProductMapper;
import org.shixuan.inventory.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Override
    public PageResult<Product> getProductPage(int pageNum, int pageSize, String keyword, Long categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.selectList(keyword, categoryId);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
    
    @Override
    public Product getProductById(Long id) {
        return productMapper.selectById(id);
    }
    
    @Override
    public Product getProductByCode(String productCode) {
        return productMapper.selectByCode(productCode);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addProduct(Product product) {
        // 检查商品编码是否已存在
        Product existing = productMapper.selectByCode(product.getProductCode());
        if (existing != null) {
            throw new ServiceException("商品编码已存在");
        }
        
        // 设置默认值
        if (product.getStatus() == null) {
            product.setStatus(1); // 默认在售
        }
        
        // 插入商品
        int rows = productMapper.insert(product);
        if (rows <= 0) {
            throw new ServiceException("添加商品失败");
        }
        
        // 初始化库存记录
        Inventory inventory = new Inventory();
        inventory.setProductId(product.getId());
        inventory.setQuantity(0); // 初始库存为0
        inventoryMapper.insert(inventory);
        
        return product.getId();
    }
    
    @Override
    public boolean updateProduct(Product product) {
        // 检查商品是否存在
        Product existing = productMapper.selectById(product.getId());
        if (existing == null) {
            throw new ServiceException("商品不存在");
        }
        
        // 如果修改了商品编码，检查新编码是否已存在
        if (product.getProductCode() != null && !product.getProductCode().equals(existing.getProductCode())) {
            Product sameCode = productMapper.selectByCode(product.getProductCode());
            if (sameCode != null) {
                throw new ServiceException("商品编码已存在");
            }
        }
        
        int rows = productMapper.update(product);
        return rows > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProduct(Long id) {
        // 检查商品是否存在
        Product existing = productMapper.selectById(id);
        if (existing == null) {
            throw new ServiceException("商品不存在");
        }
        
        // 检查商品库存
        Inventory inventory = inventoryMapper.selectByProductId(id);
        if (inventory != null && inventory.getQuantity() > 0) {
            throw new ServiceException("商品库存不为0，不能删除");
        }
        
        // 删除商品
        int rows = productMapper.deleteById(id);
        return rows > 0;
    }
    
    @Override
    public boolean updateStatus(Long id, Integer status) {
        // 检查商品是否存在
        Product existing = productMapper.selectById(id);
        if (existing == null) {
            throw new ServiceException("商品不存在");
        }
        
        int rows = productMapper.updateStatus(id, status);
        return rows > 0;
    }
    
    @Override
    public List<Product> getProductsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return productMapper.selectByIds(ids);
    }
} 