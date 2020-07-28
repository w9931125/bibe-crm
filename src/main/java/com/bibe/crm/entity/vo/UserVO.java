package com.bibe.crm.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Integer id;

    /**
     * 手机号（账号）
     */
    private String phone;
    /**
     * 姓名
     */
    private String name;

    /**
     * 角色id
     */
    private Integer roleId;


    private String roleName;

    /**
     * 部门id
     */
    private Integer deptId;

    private String  deptName;

    /**
     * 登录ip地址
     */
    private String host;

    /**
     * 录入客户剩余次数
     */
    private Integer number;

    /**
     * 0:启动 1:禁用
     */
    private Integer status;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
