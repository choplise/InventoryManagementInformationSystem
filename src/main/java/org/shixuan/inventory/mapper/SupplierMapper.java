package org.shixuan.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import org.shixuan.inventory.domain.Supplier;

import java.util.List;

/**
 * 供应商Mapper接口
 */
public interface SupplierMapper {

    /**
     * 查询供应商列表
     *
     * @param keyword 关键字(编码或名称)
     * @return 供应商列表
     */
    List<Supplier> selectList(@Param("keyword") String keyword);

    /**
     * 通过ID查询供应商
     *
     * @param id 供应商ID
     * @return 供应商信息
     */
    Supplier selectById(Long id);
    
    /**
     * 通过编码查询供应商
     *
     * @param supplierCode 供应商编码
     * @return 供应商信息
     */
    Supplier selectByCode(String supplierCode);

    /**
     * 新增供应商
     *
     * @param supplier 供应商信息
     * @return 影响行数
     */
    int insert(Supplier supplier);

    /**
     * 更新供应商
     *
     * @param supplier 供应商信息
     * @return 影响行数
     */
    int update(Supplier supplier);

    /**
     * 删除供应商
     *
     * @param id 供应商ID
     * @return 影响行数
     */
    int deleteById(Long id);
} 