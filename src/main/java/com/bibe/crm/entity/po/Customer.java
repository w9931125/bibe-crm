package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * 客户表
 */
@Data
public class Customer {
    private Integer id;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户行业
     */
    private String industry;

    /**
     * 负责人id
     */
    private Integer userId;

    /**
     * 客户分组id 0代表无
     */
    private Integer groupId;

    /**
     * 城市id
     */
    private Integer areaId;

    /**
     * 意向度:0:成交客户,1:A+,2:A-,3:B+,4:B-,5:C+,6:D+,7:E
     */
    private Integer intention;

    /**
     * 跟进次数
     */
    private Integer progressNum;

    /**
     * 客户类别:0代理商 1直客 2采购方
     */
    private Integer type;

    /**
     * 1公客
     */
    private Integer customerType;

    /**
     * 0未使用 1使用过
     */
    private Integer useType;

    /**
     * 客户地址
     */
    private String address;

    /**
     * 客户介绍
     */
    private String remarks;

    /**
     * 删除状态1:已删除
     */
    private Integer status;

    /**
     * 转为公客时间
     */
    private Date changeTime;

    /**
     * 最后跟进时间
     */
    private Date latsTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}