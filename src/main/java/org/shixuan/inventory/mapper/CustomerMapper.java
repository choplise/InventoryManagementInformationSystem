package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.Customer;

import java.util.List;

/**
 * 客户Mapper接口
 */
public interface CustomerMapper {

    /**
     * 查询客户列表
     *
     * @param keyword 关键字(编码或名称)
     * @return 客户列表
     */
    List<Customer> selectList(@Param("keyword") String keyword);

    /**
     * 通过ID查询客户
     *
     * @param id 客户ID
     * @return 客户信息
     */
    Customer selectById(Long id);

    /**
     * 通过编码查询客户
     *
     * @param customerCode 客户编码
     * @return 客户信息
     */
    Customer selectByCode(String customerCode);

    /**
     * 新增客户
     *
     * @param customer 客户信息
     * @return 影响行数
     */
    int insert(Customer customer);

    /**
     * 更新客户
     *
     * @param customer 客户信息
     * @return 影响行数
     */
    int update(Customer customer);

    /**
     * 删除客户
     *
     * @param id 客户ID
     * @return 影响行数
     */
    int deleteById(Long id);
} 