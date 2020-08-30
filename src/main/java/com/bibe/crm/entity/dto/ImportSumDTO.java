package com.bibe.crm.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImportSumDTO implements Serializable {
    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String name;

    /**
     * 地区
     */
    @Excel(name = "所在城市")
    private String areaName;

    @Excel(name = "行业")
    private String industry;


    /**
     * 客户地址
     */
    @Excel(name = "公司地址")
    private String address;


    /**
     * 客户满意度,0成交客户,1A+,2A-,3B+,4B-,5C+,6C-,7D,8E
     */
    @Excel(name = "意向度")
    private String intention;


    /**
     * 客户类别:0代理商 1直客 2采购方
     */
    @Excel(name = "客户类别")
    private String type;

    /**
     * 联系人
     */
    @Excel(name = "[联系人]姓名")
    private String contactName;


    /**
     * 职务
     */
    @Excel(name = "[联系人]职务")
    private String position;

    /**
     * 职务
     */
    @Excel(name = "[联系人]手机号")
    private String phone;

    /**
     * 座机
     */
    @Excel(name = "[联系人]座机")
    private String landline;

    /**
     * 邮箱
     */

    @Excel(name = "[联系人]邮箱")
    private String email;

    /**
     * qq号
     */
    @Excel(name = "[联系人]QQ")
    private String qq;
}
