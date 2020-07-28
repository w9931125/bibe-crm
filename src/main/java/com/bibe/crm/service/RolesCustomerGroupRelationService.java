package com.bibe.crm.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.dao.RolesCustomerGroupRelationMapper;
import com.bibe.crm.entity.po.RolesCustomerGroupRelation;

@Service
public class RolesCustomerGroupRelationService {

    @Resource
    private RolesCustomerGroupRelationMapper rolesCustomerGroupRelationMapper;


    public int insert(RolesCustomerGroupRelation record) {
        return rolesCustomerGroupRelationMapper.insert(record);
    }


    public int insertSelective(RolesCustomerGroupRelation record) {
        return rolesCustomerGroupRelationMapper.insertSelective(record);
    }

}

