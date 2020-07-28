package com.bibe.crm.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.dao.RolesDepartmentRelationMapper;
import com.bibe.crm.entity.po.RolesDepartmentRelation;

@Service
public class RolesDepartmentRelationService {

    @Resource
    private RolesDepartmentRelationMapper rolesDepartmentRelationMapper;




    public int insert(RolesDepartmentRelation record) {
        return rolesDepartmentRelationMapper.insert(record);
    }


    public int insertSelective(RolesDepartmentRelation record) {
        return rolesDepartmentRelationMapper.insertSelective(record);
    }

}

