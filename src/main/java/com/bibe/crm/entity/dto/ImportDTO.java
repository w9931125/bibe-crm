package com.bibe.crm.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ImportDTO {
    /**
     * 客户名称
     */
    @Excel(name = "客户名称", orderNum = "1", width = 15)
    private String name;

    /**
     * 地区
     */
    @Excel(name = "所在城市", orderNum = "2", width = 15)
    private String areaName;


    private String industry;


    /**
     * 客户地址
     */
    private String address;


    /**
     * 意向度:0:成交客户,1:A+,2:A-,3:B+,4:B-,5:C+,6:D+,7:E
     */
    private String intention;


    /**
     * 客户类别:0代理商 1直客 2采购方
     */
    private Integer type;

    /**
     * 联系人
     */
    private String ContactName;


    /**
     * 职务
     */
    private String position;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 座机
     */
    private String landline;

    /**
     * 邮箱
     */
    private String email;

    /**
     * qq号
     */
    private String qq;
}
