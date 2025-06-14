package org.shixuan.inventory.service;

import org.shixuan.inventory.domain.Customer;
import org.shixuan.inventory.dto.PageResult;

import java.util.List;

/**
 * 客户服务接口
 */
public interface CustomerService {

    /**
     * 分页查询客户列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @param keyword  关键字
     * @return 分页客户列表
     */
    PageResult<Customer> getCustomerPage(int pageNum, int pageSize, String keyword);

    /**
     * 获取所有客户
     * @return
     */
    List<Customer> listAll();

    /**
     * 根据ID获取客户
     *
     * @param id 客户ID
     * @return 客户信息
     */
    Customer getCustomerById(Long id);

    /**
     * 添加客户
     *
     * @param customer 客户信息
     * @return 新增的客户ID
     */
    Long addCustomer(Customer customer);

    /**
     * 更新客户
     *
     * @param customer 客户信息
     * @return 是否成功
     */
    boolean updateCustomer(Customer customer);

    /**
     * 删除客户
     *
     * @param id 客户ID
     * @return 是否成功
     */
    boolean deleteCustomer(Long id);
} 