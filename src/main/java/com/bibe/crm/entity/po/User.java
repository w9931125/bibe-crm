package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * 用户
 */
@Data
public class User {
    private Integer id;

    /**
     * 手机号（账号）
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 部门id
     */
    private Integer deptId;

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