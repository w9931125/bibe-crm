package com.bibe.crm.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class PublicCustomerVO implements Serializable {

    @Excel(name = "编号", orderNum = "0", width = 15)
    private Integer id;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称", orderNum = "1", width = 15)
    private String name;

    private Integer userId;
    /**
     * 地区
     */
    @Excel(name = "所在城市", orderNum = "2", width = 15)
    private String areaName;

    /**
     * 意向度:0:成交客户,1:A+,2:A-,3:B+,4:B-,5:C+,6:D+,7:E
     */
    @Excel(name = "意向度", replace = { "成交客户_0", "A+_1","A-_2","B+_3","B-_4","C+_5","C-_6","D_7","E_8","无_9" },orderNum = "3",width = 15)
    private Integer intention;

    /**
     * 客户类别:0代理商 1直客 2采购方
     */
    @Excel(name = "客户类别", replace = { "代理商_0", "直客_1","采购方_2","无_3" }, orderNum = "4", width = 15)
    private Integer type;

    /**
     * 手机号
     */
    @Excel(name = "手机号", orderNum = "5", width = 15)
    private String phone;

    /**
     * 最后跟进时间
     */
    @Excel(name = "最后跟进时间", orderNum = "6", width = 15 ,databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd HH:mm:ss" )
    private Date latsTime;

    /**
     * 负责人名称
     */
    @Excel(name = "负责人名称", orderNum = "8", width = 15)
    private String userName;

    /**
     * 下次跟进时间
     */

    private Date nextTime;

    /**
     * 转为公客时间
     */
    @Excel(name = "转为公客时间", orderNum = "7", width = 15 ,databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd HH:mm:ss" )
    private Date changeTime;

    /**
     * 跟进结果
     */
    @Excel(name = "最后跟进内容", orderNum = "9", width = 15)
    private String remarks;

    /**
     * 跟进次数
     */
    @Excel(name = "跟进次数", orderNum = "10", width = 15)
    private Integer progressNum;

    /**
     * 0普通 1公客
     */
    private Integer customerType;

    /**
     * 0未使用 1使用过
     */
    private Integer useType;

    /**
     * 录入时间
     */
    @Excel(name = "录入时间", orderNum = "11", width = 15 ,databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;
}
