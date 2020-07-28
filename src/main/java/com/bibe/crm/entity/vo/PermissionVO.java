package com.bibe.crm.entity.vo;

import com.bibe.crm.entity.po.Permission;
import com.bibe.crm.entity.po.RolesCustomerGroupRelation;
import com.bibe.crm.entity.po.RolesDepartmentRelation;
import com.bibe.crm.entity.po.RolesPermissionRelation;
import lombok.Data;

import java.util.List;

@Data
public class PermissionVO {

    /**
     * 职位指定查看部门权限
     * 禁止查看为0
     * 查看所有为-1
     * 只看自己为-2
     * 同步客户-3
     * 其他按部门id记录
     */
    private List<RolesDepartmentRelation> rolesDepartmentRelationList;

    /**
     * 权限id
     */
    //private List<RolesPermissionRelation> PermissionList;
    private List<Permission> permissionList;


    /**
     * 录入客户剩余次数 0禁止 -1无限制
     */
    private Integer number;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 公客分组权限
     * -1为全部客户
     * -2为用户所属部门
     */
    private List<RolesCustomerGroupRelation> groupRelationList;


}
