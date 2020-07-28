package com.bibe.crm.entity.po;

import lombok.Data;

/**
 * 职务角色与公客分组关系表
 */
@Data
public class RolesCustomerGroupRelation {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * -1为全部客户 -2为用户所属部门
     */
    private Integer customerGroupId;
}