package com.bibe.crm.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.bibe.crm.entity.po.CustomerGroupDepartmentRelation;

public interface CustomerGroupDepartmentRelationMapper {
    int insert(CustomerGroupDepartmentRelation record);

    int insertSelective(CustomerGroupDepartmentRelation record);

    int insertList(@Param("list")List<CustomerGroupDepartmentRelation> list);

    int deleteByCustomerGroupId(@Param("customerGroupId")Integer customerGroupId);

    /**
     * 部门删除验证公客
     * @param deptId
     * @return
     */
    int findCountByDeptId(@Param("deptId")Integer deptId);

    /**
     * 分组删除验证部门
     * @param customerGroupId
     * @return
     */
    int findCountByCustomerGroupId(@Param("customerGroupId")Integer customerGroupId);

    /**
     * 查看分组所有的部门
     * @param customerGroupId
     * @return
     */
    List<Map<String,Object>> findAllByCustomerGroupId(@Param("customerGroupId")Integer customerGroupId);


    /**
     * 获取部门拼接名称
     * @param customerGroupId
     * @return
     */
    String findDeptNamesByGroupId(@Param("customerGroupId")Integer customerGroupId);

    List<Integer> findCustomerGroupIdByDeptId(@Param("deptId")Integer deptId);


}