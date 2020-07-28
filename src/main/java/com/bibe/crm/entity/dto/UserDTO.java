package com.bibe.crm.entity.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户
 */
@Data
@ToString
public class UserDTO {
    /**
     * 手机号（账号）
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 重复密码
     */
    private String repeatPassword;

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
     * 0启用 1禁止
     */
    private String status;

    private Integer id;
}