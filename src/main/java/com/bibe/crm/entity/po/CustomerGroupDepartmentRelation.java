package com.bibe.crm.entity.po;

import lombok.Data;

/**
    * 公客分组与部门关联表
    */
@Data
public class CustomerGroupDepartmentRelation {
    /**
    * 公客分组id
    */
    private Integer customerGroupId;

    /**
    * 部门id
    */
    private Integer deptId;
}