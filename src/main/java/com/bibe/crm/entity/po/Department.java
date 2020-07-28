package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
    * 部门
    */
@Data
public class Department {
    private Integer id;

    /**
    * 部门名称
    */
    private String name;

    /**
    * 序号
    */
    private Integer num;

    /**
    * 等级
    */
    private Integer level;

    /**
    * 父级id
    */
    private Integer parentId;

    private Date createTime;

    private Date updateTime;
}