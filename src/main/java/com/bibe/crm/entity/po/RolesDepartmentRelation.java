package com.bibe.crm.entity.po;

import lombok.Data;

/**
 * 职务角色与部门权限关系表
 */
@Data
public class RolesDepartmentRelation {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 部门id -1为全部客户
     */
    private Integer deptId;

    /**
     * 0全部客户 1联系跟进
     */
    private Integer type;
}