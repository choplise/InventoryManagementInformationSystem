package org.shixuan.inventory.mapper;

import org.shixuan.inventory.domain.ProductCategory;

import java.util.List;

/**
 * 商品分类Mapper接口
 */
public interface ProductCategoryMapper {
    
    /**
     * 查询所有商品分类
     * 
     * @return 商品分类列表
     */
    List<ProductCategory> selectAll();
    
    /**
     * 通过ID查询商品分类
     * 
     * @param id 分类ID
     * @return 商品分类
     */
    ProductCategory selectById(Long id);
    
    /**
     * 通过父ID查询子分类
     * 
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<ProductCategory> selectByParentId(Long parentId);
    
    /**
     * 新增商品分类
     * 
     * @param category 商品分类
     * @return 影响行数
     */
    int insert(ProductCategory category);
    
    /**
     * 更新商品分类
     * 
     * @param category 商品分类
     * @return 影响行数
     */
    int update(ProductCategory category);
    
    /**
     * 删除商品分类
     * 
     * @param id 分类ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 检查是否有子分类
     * 
     * @param id 分类ID
     * @return 子分类数量
     */
    int countChildCategories(Long id);
    
    /**
     * 检查分类下是否有商品
     * 
     * @param id 分类ID
     * @return 商品数量
     */
    int countProductsByCategory(Long id);
} 