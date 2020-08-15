package com.bibe.crm.dao;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.bibe.crm.entity.po.CustomerContact;

public interface CustomerContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerContact record);

    int insertSelective(CustomerContact record);

    CustomerContact selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerContact record);

    int updateByPrimaryKey(CustomerContact record);

    int insertList(@Param("list")List<CustomerContact> list);

    int updateByCustomerIdin(@Param("updated")CustomerContact updated,@Param("customerIdCollection")Collection<Integer> customerIdCollection);

    CustomerContact findAllById(@Param("id")Integer id);

    CustomerContact findAllByCustomerId(@Param("customerId")Integer cId);

    List<CustomerContact> list();

    Map<String,Object> show(Integer id);
}