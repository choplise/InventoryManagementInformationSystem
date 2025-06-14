package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.Product;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.enums.ResultCode;
import org.shixuan.inventory.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;
    
    /**
     * 分页查询商品列表
     */
    @GetMapping("/page")
    public Result<PageResult<Product>> getProductPage(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Long categoryId) {
        
        PageResult<Product> pageResult = productService.getProductPage(pageNum, pageSize, keyword, categoryId);
        return Result.success(pageResult);
    }
    
    /**
     * 根据ID获取商品
     */
    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return Result.failed(ResultCode.PRODUCT_NOT_EXIST);
        }
        return Result.success(product);
    }
    
    /**
     * 根据商品编码获取商品
     */
    @GetMapping("/code/{productCode}")
    public Result<Product> getProductByCode(@PathVariable("productCode") String productCode) {
        Product product = productService.getProductByCode(productCode);
        if (product == null) {
            return Result.failed(ResultCode.PRODUCT_NOT_EXIST);
        }
        return Result.success(product);
    }
    
    /**
     * 添加商品
     */
    @PostMapping
    public Result<Long> addProduct(@RequestBody Product product) {
        Long id = productService.addProduct(product);
        return Result.success(id);
    }
    
    /**
     * 更新商品
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        boolean success = productService.updateProduct(product);
        return Result.success(success);
    }
    
    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteProduct(@PathVariable("id") Long id) {
        boolean success = productService.deleteProduct(id);
        return Result.success(success);
    }
    
    /**
     * 更新商品状态
     */
    @PutMapping("/{id}/status/{status}")
    public Result<Boolean> updateStatus(
            @PathVariable("id") Long id, 
            @PathVariable("status") Integer status) {
        boolean success = productService.updateStatus(id, status);
        return Result.success(success);
    }
} 