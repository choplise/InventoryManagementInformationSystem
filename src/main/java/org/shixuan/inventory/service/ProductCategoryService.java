package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.ProductCategory;

import java.util.List;

/**
 * 商品分类服务接口
 */
public interface ProductCategoryService {
    
    /**
     * 获取所有商品分类
     * 
     * @return 商品分类列表
     */
    List<ProductCategory> getAllCategories();
    
    /**
     * 获取商品分类树
     * 
     * @return 商品分类树
     */
    List<ProductCategory> getCategoryTree();
    
    /**
     * 根据ID获取商品分类
     * 
     * @param id 分类ID
     * @return 商品分类信息
     */
    ProductCategory getCategoryById(Long id);
    
    /**
     * 获取子分类列表
     * 
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<ProductCategory> getChildCategories(Long parentId);
    
    /**
     * 添加商品分类
     * 
     * @param category 商品分类信息
     * @return 新增的分类ID
     */
    Long addCategory(ProductCategory category);
    
    /**
     * 更新商品分类
     * 
     * @param category 商品分类信息
     * @return 是否成功
     */
    boolean updateCategory(ProductCategory category);
    
    /**
     * 删除商品分类
     * 
     * @param id 分类ID
     * @return 是否成功
     */
    boolean deleteCategory(Long id);
} 