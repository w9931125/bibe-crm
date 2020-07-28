package com.bibe.crm.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.bibe.crm.entity.po.RolesCustomerGroupRelation;

public interface RolesCustomerGroupRelationMapper {
    int insert(RolesCustomerGroupRelation record);

    int insertSelective(RolesCustomerGroupRelation record);

    int insertList(@Param("list")List<RolesCustomerGroupRelation> list);

    int deleteByRoleId(@Param("roleId")Integer roleId);

    List<RolesCustomerGroupRelation> selectAllByRoleId(@Param("roleId")Integer roleId);


}