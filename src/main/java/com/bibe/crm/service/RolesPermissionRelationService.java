package com.bibe.crm.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.RolesPermissionRelation;
import com.bibe.crm.dao.RolesPermissionRelationMapper;

@Service
public class RolesPermissionRelationService {

    @Resource
    private RolesPermissionRelationMapper rolesPermissionRelationMapper;

    public int insert(RolesPermissionRelation record) {
        return rolesPermissionRelationMapper.insert(record);
    }



}

