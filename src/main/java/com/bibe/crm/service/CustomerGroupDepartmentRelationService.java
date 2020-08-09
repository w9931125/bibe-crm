package com.bibe.crm.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.CustomerGroupDepartmentRelation;
import com.bibe.crm.dao.CustomerGroupDepartmentRelationMapper;
@Service
public class CustomerGroupDepartmentRelationService{

    @Resource
    private CustomerGroupDepartmentRelationMapper customerGroupDepartmentRelationMapper;

    
    public int insert(CustomerGroupDepartmentRelation record) {
        return customerGroupDepartmentRelationMapper.insert(record);
    }

    
    public int insertSelective(CustomerGroupDepartmentRelation record) {
        return customerGroupDepartmentRelationMapper.insertSelective(record);
    }

}
