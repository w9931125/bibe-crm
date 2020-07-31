package com.bibe.crm.entity.dto;

import com.bibe.crm.entity.po.RolesCustomerGroupRelation;
import com.bibe.crm.entity.po.RolesDepartmentRelation;
import com.bibe.crm.entity.po.RolesPermissionRelation;
import lombok.Data;

import java.util.List;


@Data
public class PermissionDTO {

    /**
     * 职位指定查看部门权限
     * 禁止查看为0
     * 查看所有为-1
     * 只看自己为-2
     * 同步客户-3
     * 其他按部门id记录
     */
    private List<RolesDepartmentRelation> rolesDepartmentRelationList;

    private Integer rolesDepartId;

    /**
     * 权限id
     */
    private List<RolesPermissionRelation> permissionList;


    /**
     * 录入客户剩余次数 0禁止 -1无限制
     */
    private Integer number=0;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 1客户资料 2联系跟进 3部门员工/职位权限
     */
    private Integer flag;

    /**
     * 公客分组权限
     * -1为全部客户
     * -2为用户所属部门
     */
    private List<RolesCustomerGroupRelation> groupRelationList;


    /**
     * 修改 状态
     */
    private List<PermissionUpdateDTO> permissionUpdateDTO;
}
