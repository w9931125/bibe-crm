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
}