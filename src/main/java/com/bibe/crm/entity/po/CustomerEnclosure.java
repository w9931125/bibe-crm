package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
    * 客户附件表
    */
@Data
public class CustomerEnclosure {
    private Integer id;

    /**
    * 客户id
    */
    private Integer customerId;

    /**
    * 附件名称
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