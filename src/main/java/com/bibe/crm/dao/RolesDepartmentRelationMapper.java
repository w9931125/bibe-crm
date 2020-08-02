package com.bibe.crm.dao;

import com.bibe.crm.entity.po.RolesDepartmentRelation;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface RolesDepartmentRelationMapper {
    int insert(RolesDepartmentRelation record);

    int insertSelective(RolesDepartmentRelation record);

    int insertList(@Param("list") List<RolesDepartmentRelation> list);

    int deleteByRoleId(@Param("roleId")Integer roleId,@Param("type")Integer type);

    List<RolesDepartmentRelation> selectAllByRoleIdAndType(@Param("roleId")Integer roleId,@Param("type")Integer type);

}