package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
    * 文件表
    */
@Data
public class Files {
    private Integer id;

    /**
    * 文件名称
    */
    private String name;

    /**
    * 别名
    */
    private String alias;

    /**
    * 附件地址
    */
    private String path;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;
}