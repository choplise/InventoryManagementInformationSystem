package org.shixuan.inventory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.shixuan.inventory.domain.Customer;
import org.shixuan.inventory.dto.PageResult;
import org.shixuan.inventory.exception.ServiceException;
import org.shixuan.inventory.mapper.CustomerMapper;
import org.shixuan.inventory.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户服务实现类
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public PageResult<Customer> getCustomerPage(int pageNum, int pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Customer> list = customerMapper.selectList(keyword);
        PageInfo<Customer> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    @Override
    public List<Customer> listAll() {
        return customerMapper.selectList(null);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public Long addCustomer(Customer customer) {
        Customer existing = customerMapper.selectByCode(customer.getCustomerCode());
        if (existing != null) {
            throw new ServiceException("客户编码已存在");
        }
        if (customer.getStatus() == null) {
            customer.setStatus(1);
        }
        customerMapper.insert(customer);
        return customer.getId();
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        Customer existing = customerMapper.selectById(customer.getId());
        if (existing == null) {
            throw new ServiceException("客户不存在");
        }
        if (customer.getCustomerCode() != null && !customer.getCustomerCode().equals(existing.getCustomerCode())) {
            Customer sameCode = customerMapper.selectByCode(customer.getCustomerCode());
            if (sameCode != null) {
                throw new ServiceException("客户编码已存在");
            }
        }
        return customerMapper.update(customer) > 0;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        Customer existing = customerMapper.selectById(id);
        if (existing == null) {
            throw new ServiceException("客户不存在");
        }
        // TODO: 检查客户是否有关联的销售单
        return customerMapper.deleteById(id) > 0;
    }
} 