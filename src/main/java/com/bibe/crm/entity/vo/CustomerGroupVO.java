package com.bibe.crm.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * 公客分组
 */
@Data
public class CustomerGroupVO {
    private Integer id;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 自动转交状态:0未开启 1开启
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 部门名
     */
    private String deptNames;
}