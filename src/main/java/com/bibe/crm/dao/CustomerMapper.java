package com.bibe.crm.dao;

import com.bibe.crm.entity.po.Customer;

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
}