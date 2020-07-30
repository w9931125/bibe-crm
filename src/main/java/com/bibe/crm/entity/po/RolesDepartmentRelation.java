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
     * 部门id -1为全部客户 0为禁止 -2只看自己负责 -3同步客户 -4与自己同一个部门 -5与自己同一个部门及下级部门
     */
    private Integer deptId;

    /**
     * 0全部客户 1联系跟进
     */
    private Integer type;
}