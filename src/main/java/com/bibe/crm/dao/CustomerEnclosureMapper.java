package com.bibe.crm.dao;

import com.bibe.crm.entity.po.CustomerEnclosure;

public interface CustomerEnclosureMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerEnclosure record);

    int insertSelective(CustomerEnclosure record);

    CustomerEnclosure selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerEnclosure record);

    int updateByPrimaryKey(CustomerEnclosure record);
}