package com.bibe.crm.dao;

import com.bibe.crm.entity.po.CustomerProgress;

public interface CustomerProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerProgress record);

    int insertSelective(CustomerProgress record);

    CustomerProgress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerProgress record);

    int updateByPrimaryKey(CustomerProgress record);
}