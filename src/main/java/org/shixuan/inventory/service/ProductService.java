package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.Product;
import org.shixuan.inventory.dto.PageResult;

import java.util.List;

/**
 * 商品服务接口
 */
public interface ProductService {
    
    /**
     * 分页查询商品列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @param keyword 关键字
     * @param categoryId 分类ID
     * @return 分页商品列表
     */
    PageResult<Product> getProductPage(int pageNum, int pageSize, String keyword, Long categoryId);
    
    /**
     * 根据ID获取商品
     * 
     * @param id 商品ID
     * @return 商品信息
     */
    Product getProductById(Long id);
    
    /**
     * 根据商品编码获取商品
     * 
     * @param productCode 商品编码
     * @return 商品信息
     */
    Product getProductByCode(String productCode);
    
    /**
     * 添加商品
     * 
     * @param product 商品信息
     * @return 新增的商品ID
     */
    Long addProduct(Product product);
    
    /**
     * 更新商品
     * 
     * @param product 商品信息
     * @return 是否成功
     */
    boolean updateProduct(Product product);
    
    /**
     * 删除商品
     * 
     * @param id 商品ID
     * @return 是否成功
     */
    boolean deleteProduct(Long id);
    
    /**
     * 更新商品状态
     * 
     * @param id 商品ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);
    
    /**
     * 根据多个ID获取商品列表
     * 
     * @param ids 商品ID列表
     * @return 商品列表
     */
    List<Product> getProductsByIds(List<Long> ids);
} 