package org.shixuan.inventory.controller;

import org.shixuan.inventory.domain.Customer;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.dto.Result;
import org.shixuan.inventory.enums.ResultCode;
import org.shixuan.inventory.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理API
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 分页查询客户列表
     */
    @GetMapping("/page")
    public Result<PageResult<Customer>> getCustomerPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword) {
        PageResult<Customer> pageResult = customerService.getCustomerPage(pageNum, pageSize, keyword);
        return Result.success(pageResult);
    }

    /**
     * 获取所有客户
     */
    @GetMapping("/list")
    public Result<List<Customer>> listAll() {
        List<Customer> customers = customerService.listAll();
        return Result.success(customers);
    }

    /**
     * 根据ID获取客户详情
     */
    @GetMapping("/{id}")
    public Result<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return Result.failed(ResultCode.CUSTOMER_NOT_EXIST);
        }
        return Result.success(customer);
    }

    /**
     * 新增客户
     */
    @PostMapping
    public Result<Long> addCustomer(@RequestBody Customer customer) {
        Long customerId = customerService.addCustomer(customer);
        return Result.success(customerId);
    }

    /**
     * 更新客户
     */
    @PutMapping("/{id}")
    public Result<Void> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        boolean success = customerService.updateCustomer(customer);
        return success ? Result.success() : Result.failed("更新客户失败");
    }

    /**
     * 删除客户
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCustomer(@PathVariable Long id) {
        boolean success = customerService.deleteCustomer(id);
        return success ? Result.success() : Result.failed("删除客户失败");
    }
} 