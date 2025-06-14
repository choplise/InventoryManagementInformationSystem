package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.ProductCategory;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.enums.ResultCode;
import org.shixuan.inventory.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制器
 */
@RestController
@RequestMapping("/category")
@PreAuthorize("hasAuthority('data:product')")
public class ProductCategoryController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryController.class);
    
    @Autowired
    private ProductCategoryService categoryService;
    
    /**
     * 获取所有商品分类
     */
    @GetMapping("/list")
    public Result<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }
    
    /**
     * 获取商品分类树
     */
    @GetMapping("/tree")
    public Result<List<ProductCategory>> getCategoryTree() {
        List<ProductCategory> categoryTree = categoryService.getCategoryTree();
        return Result.success(categoryTree);
    }
    
    /**
     * 根据ID获取商品分类
     */
    @GetMapping("/{id}")
    public Result<ProductCategory> getCategoryById(@PathVariable("id") Long id) {
        ProductCategory category = categoryService.getCategoryById(id);
        if (category == null) {
            return Result.failed(ResultCode.PRODUCT_CATEGORY_NOT_EXIST);
        }
        return Result.success(category);
    }
    
    /**
     * 获取子分类
     */
    @GetMapping("/children/{parentId}")
    public Result<List<ProductCategory>> getChildCategories(@PathVariable("parentId") Long parentId) {
        List<ProductCategory> children = categoryService.getChildCategories(parentId);
        return Result.success(children);
    }
    
    /**
     * 添加商品分类
     */
    @PostMapping
    public Result<Long> addCategory(@RequestBody ProductCategory category) {
        Long id = categoryService.addCategory(category);
        return Result.success(id);
    }
    
    /**
     * 更新商品分类
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateCategory(@PathVariable("id") Long id, @RequestBody ProductCategory category) {
        category.setId(id);
        boolean success = categoryService.updateCategory(category);
        return Result.success(success);
    }
    
    /**
     * 删除商品分类
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteCategory(@PathVariable("id") Long id) {
        boolean success = categoryService.deleteCategory(id);
        return Result.success(success);
    }
} 