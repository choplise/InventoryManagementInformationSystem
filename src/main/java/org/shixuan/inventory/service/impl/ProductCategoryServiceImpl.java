package org.shixuan.inventory.service.impl;

import org.shixuan.inventory.domain.ProductCategory;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.ProductCategoryMapper;
import org.shixuan.inventory.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类服务实现类
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
    
    @Autowired
    private ProductCategoryMapper categoryMapper;
    
    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryMapper.selectAll();
    }
    
    @Override
    public List<ProductCategory> getCategoryTree() {
        List<ProductCategory> allCategories = categoryMapper.selectAll();
        List<ProductCategory> rootCategories = new ArrayList<>();
        Map<Long, List<ProductCategory>> childrenMap = new HashMap<>();
        
        // 将所有分类按照父ID分组
        for (ProductCategory category : allCategories) {
            if (category.getParentId() == 0) {
                rootCategories.add(category);
            } else {
                if (!childrenMap.containsKey(category.getParentId())) {
                    childrenMap.put(category.getParentId(), new ArrayList<>());
                }
                childrenMap.get(category.getParentId()).add(category);
            }
        }
        
        // 处理子分类
        for (ProductCategory root : rootCategories) {
            if (childrenMap.containsKey(root.getId())) {
                root.setChildren(childrenMap.get(root.getId()));
                // 处理二级分类的子分类
                for (ProductCategory child : root.getChildren()) {
                    if (childrenMap.containsKey(child.getId())) {
                        child.setChildren(childrenMap.get(child.getId()));
                    }
                }
            }
        }
        
        return rootCategories;
    }
    
    @Override
    public ProductCategory getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }
    
    @Override
    public List<ProductCategory> getChildCategories(Long parentId) {
        return categoryMapper.selectByParentId(parentId);
    }
    
    @Override
    public Long addCategory(ProductCategory category) {
        if (category.getParentId() == null) {
            category.setParentId(0L);
        }
        
        if (category.getSortOrder() == null) {
            category.setSortOrder(0);
        }
        
        int rows = categoryMapper.insert(category);
        if (rows > 0) {
            return category.getId();
        }
        throw new ServiceException("添加商品分类失败");
    }
    
    @Override
    public boolean updateCategory(ProductCategory category) {
        ProductCategory existing = categoryMapper.selectById(category.getId());
        if (existing == null) {
            throw new ServiceException("商品分类不存在");
        }
        
        // 避免将分类的父级设为自己或其子分类
        if (category.getParentId() != null && !category.getParentId().equals(0L)) {
            if (category.getParentId().equals(category.getId())) {
                throw new ServiceException("不能将分类的父级设为自己");
            }
            
            // 检查是否设置为其子分类
            List<ProductCategory> children = getChildCategories(category.getId());
            for (ProductCategory child : children) {
                if (child.getId().equals(category.getParentId())) {
                    throw new ServiceException("不能将分类的父级设为其子分类");
                }
            }
        }
        
        int rows = categoryMapper.update(category);
        return rows > 0;
    }
    
    @Override
    public boolean deleteCategory(Long id) {
        // 检查是否有子分类
        int childCount = categoryMapper.countChildCategories(id);
        if (childCount > 0) {
            throw new ServiceException("该分类下有子分类，不能删除");
        }
        
        // 检查是否有商品使用该分类
        int productCount = categoryMapper.countProductsByCategory(id);
        if (productCount > 0) {
            throw new ServiceException("该分类下有商品，不能删除");
        }
        
        int rows = categoryMapper.deleteById(id);
        return rows > 0;
    }
} 