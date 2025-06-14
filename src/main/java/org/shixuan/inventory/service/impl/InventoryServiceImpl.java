package org.shixuan.inventory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.Inventory;
import org.shixuan.inventory.domain.InventoryRecord;
import org.shixuan.inventory.domain.Product;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.InventoryMapper;
import org.shixuan.inventory.mapper.InventoryRecordMapper;
import org.shixuan.inventory.mapper.ProductMapper;
import org.shixuan.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 库存服务实现类
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);
    
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Autowired
    private InventoryRecordMapper recordMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public PageResult<Inventory> getInventoryPage(int pageNum, int pageSize, String keyword, Integer warningStatus) {
        PageHelper.startPage(pageNum, pageSize);
        List<Inventory> list = inventoryMapper.selectList(keyword, warningStatus);
        PageInfo<Inventory> pageInfo = new PageInfo<>(list);
        
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
    
    @Override
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryMapper.selectByProductId(productId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean increaseInventory(Long productId, Integer quantity, Integer changeType, String relatedOrderNo, Long operatorId) {
        if (quantity <= 0) {
            throw new ServiceException("增加数量必须大于0");
        }
        
        // 检查商品是否存在
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new ServiceException("商品不存在");
        }
        
        // 增加库存
        Inventory inventory = inventoryMapper.selectByProductId(productId);
        if (inventory == null) {
            // 如果库存记录不存在，则创建新记录
            inventory = new Inventory();
            inventory.setProductId(productId);
            inventory.setQuantity(quantity);
            inventoryMapper.insert(inventory);
        } else {
            // 更新库存
            inventoryMapper.increaseQuantity(productId, quantity);
        }
        
        // 更新后的库存数量
        inventory = inventoryMapper.selectByProductId(productId);
        
        // 记录库存变动
        InventoryRecord record = new InventoryRecord();
        record.setProductId(productId);
        record.setChangeQuantity(quantity);
        record.setQuantityAfterChange(inventory.getQuantity());
        record.setChangeType(changeType);
        record.setRelatedOrderNo(relatedOrderNo);
        record.setOperatorId(operatorId);
        recordMapper.insert(record);
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseInventory(Long productId, Integer quantity, Integer changeType, String relatedOrderNo, Long operatorId) {
        if (quantity <= 0) {
            throw new ServiceException("减少数量必须大于0");
        }
        
        // 检查库存是否充足
        Inventory inventory = inventoryMapper.selectByProductId(productId);
        if (inventory == null || inventory.getQuantity() < quantity) {
            throw new ServiceException("商品库存不足");
        }
        
        // 减少库存
        int rows = inventoryMapper.decreaseQuantity(productId, quantity);
        if (rows <= 0) {
            throw new ServiceException("库存操作失败");
        }
        
        // 更新后的库存数量
        inventory = inventoryMapper.selectByProductId(productId);
        
        // 记录库存变动
        InventoryRecord record = new InventoryRecord();
        record.setProductId(productId);
        record.setChangeQuantity(-quantity); // 负数表示出库
        record.setQuantityAfterChange(inventory.getQuantity());
        record.setChangeType(changeType);
        record.setRelatedOrderNo(relatedOrderNo);
        record.setOperatorId(operatorId);
        recordMapper.insert(record);
        
        return true;
    }
    
    @Override
    public PageResult<InventoryRecord> getInventoryRecordPage(int pageNum, int pageSize, Long productId, Integer changeType, Date startTime, Date endTime) {
        PageHelper.startPage(pageNum, pageSize);
        List<InventoryRecord> list = recordMapper.selectList(productId, changeType, startTime, endTime);
        PageInfo<InventoryRecord> pageInfo = new PageInfo<>(list);
        
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
    
    @Override
    public boolean checkInventory(Long productId, Integer quantity) {
        Inventory inventory = inventoryMapper.selectByProductId(productId);
        return inventory != null && inventory.getQuantity() >= quantity;
    }
    
    @Override
    public List<Inventory> getWarningInventory(Integer warningStatus) {
        return inventoryMapper.selectList(null, warningStatus);
    }
} 