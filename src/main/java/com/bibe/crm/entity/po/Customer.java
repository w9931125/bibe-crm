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
     * 意向度,0成交客户,1A+,2A-,3B+,4B-,5C+,6C-,7D,8E
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
     * 使用产品0未使用 1使用过
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
     * 文件id
     */
    private String filesId;

    /**
     * 转为公客时间
     */
    private Date changeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}