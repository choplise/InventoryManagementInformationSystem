package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.Product;

import java.util.List;

/**
 * 商品Mapper接口
 */
public interface ProductMapper {
    
    /**
     * 查询商品列表
     * 
     * @param keyword 关键字(商品编码或名称)
     * @param categoryId 商品分类ID
     * @return 商品列表
     */
    List<Product> selectList(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);
    
    /**
     * 通过ID查询商品
     * 
     * @param id 商品ID
     * @return 商品信息
     */
    Product selectById(Long id);
    
    /**
     * 通过商品编码查询商品
     * 
     * @param productCode 商品编码
     * @return 商品信息
     */
    Product selectByCode(String productCode);
    
    /**
     * 查询多个商品信息
     * 
     * @param ids 商品ID列表
     * @return 商品列表
     */
    List<Product> selectByIds(@Param("ids") List<Long> ids);
    
    /**
     * 新增商品
     * 
     * @param product 商品信息
     * @return 影响行数
     */
    int insert(Product product);
    
    /**
     * 更新商品
     * 
     * @param product 商品信息
     * @return 影响行数
     */
    int update(Product product);
    
    /**
     * 删除商品
     * 
     * @param id 商品ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 更新商品状态
     * 
     * @param id 商品ID
     * @param status 状态 (1:在售, 0:停产)
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
} 