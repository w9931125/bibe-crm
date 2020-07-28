package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * 职务角色
 */
@Data
public class Roles {
    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 录入客户次数
     */
    private Integer number;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}