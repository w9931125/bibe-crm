package com.bibe.crm.dao;

import com.bibe.crm.entity.po.CustomerProgress;import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CustomerProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerProgress record);

    int insertSelective(CustomerProgress record);

    CustomerProgress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerProgress record);

    int updateByPrimaryKey(CustomerProgress record);

    /**
     * 最新跟进
     *
     * @param customerId
     * @return
     */
    CustomerProgress findNewInfo(@Param("customerId") Integer customerId);

    /**
     * 详情
     * @param id
     * @return
     */
    Map<String,Object> show(Integer id);
}