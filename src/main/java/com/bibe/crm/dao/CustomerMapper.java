package com.bibe.crm.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;import com.bibe.crm.entity.dto.FindCustomerDTO;import com.bibe.crm.entity.po.Customer;import org.apache.ibatis.annotations.Param;import java.util.Collection;import java.util.Map;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    /**
     * 判断员工下是否有客户
     */
    Integer selectCountByUserId(Integer userId);

    /**
     * 批量修改
     *
     * @param updated
     * @param idCollection
     * @return
     */
    int updateByIdIn(@Param("updated") Customer updated, @Param("idCollection") Collection<Integer> idCollection);

    /**
     * 逻辑批量删除
     *
     * @param ids
     * @return
     */
    int updateStatusByIdIn(@Param("ids")Integer[] ids);


    IPage<Map<String, Object>> pageList(FindCustomerDTO dto, Page page);
}