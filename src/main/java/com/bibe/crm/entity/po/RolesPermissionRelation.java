package com.bibe.crm.entity.po;

import lombok.Data;

/**
 * 角色与权限关系表
 */
@Data
public class RolesPermissionRelation {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer permissionId;

    /**
     1 客户资料 2联系跟进 3职位与权限 4部门与员工
     */
    private Integer type;

    /**
     * 状态 0启用 1禁止
     */
    private Integer status;
}