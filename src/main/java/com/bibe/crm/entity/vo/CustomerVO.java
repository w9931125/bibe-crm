package com.bibe.crm.entity.vo;

import lombok.Data;

import java.util.Date;


@Data
public class CustomerVO {

    private Integer id;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 地区
     */
    private String areaName;

    /**
     * 意向度:0:成交客户,1:A+,2:A-,3:B+,4:B-,5:C+,6:D+,7:E
     */
    private Integer intention;

    /**
     * 客户类别:0代理商 1直客 2采购方
     */
    private Integer type;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 最后跟进时间
     */
    private Date latsTime;

    /**
     * 负责人名称
     */
    private String UserName;

    /**
     * 下次跟进时间
     */
    private Date nextTime;

    /**
     * 跟进结果
     */
    private String remarks;

    /**
     * 跟进次数
     */
    private Integer progressNum;

    /**
     * 0普通 1公客
     */
    private Integer customerType;

    /**
     * 0未使用 1使用过
     */
    private Integer useType;
}
