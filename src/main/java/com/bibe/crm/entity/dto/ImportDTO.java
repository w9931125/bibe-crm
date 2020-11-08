package com.bibe.crm.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ImportDTO  implements IExcelDataModel, IExcelModel {
    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    @NotBlank(message = "[客户名称]不能为空")
    private String name;

    /**
     * 地区
     */
    @Excel(name = "所在城市")
    @NotBlank(message = "[所在城市]不能为空")
    private String areaName;

    @Excel(name = "行业")
    private String industry;


    /**
     * 客户地址
     */
    @Excel(name = "公司地址")
    //@NotBlank(message = "[公司地址]不能为空")
    private String address;


    /**
     * 客户满意度,0成交客户,1A+,2A-,3B+,4B-,5C+,6C-,7D,8E
     */
    @Excel(name = "意向度", replace = { "成交客户_0", "A+_1","A-_2","B+_3","B-_4","C+_5","C-_6","D_7","E_8"})
    @Pattern(regexp = "[0,1,2,3,4,5,6,7,8]", message = "客户意向度信息错误")
    private String intention;


    /**
     * 客户类别:0代理商 1直客 2采购方
     */
    @Excel(name = "客户类别", replace = { "代理商_0", "直客_1","采购方_2"})
    @Pattern(regexp = "[0,1,2]", message = "客户类别信息错误")
    private String type;

    /**
     * 联系人
     */
    @Excel(name = "[联系人]姓名")
    @NotBlank(message = "[联系人姓名]不能为空")
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
    @NotBlank(message = "[联系人手机号]不能为空")
    //@Pattern(regexp = "[0-9]{11}", message = "手机号不正确")
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

    private int rowNum;

    private String errorMsg;

    @Override
    public int getRowNum() {
        return rowNum+1;
    }

    @Override
    public void setRowNum(int i) {
       this.rowNum=i;
    }

    @Override
    public String getErrorMsg() {
        return  errorMsg;
    }

    @Override
    public void setErrorMsg(String s) {
        this.errorMsg=s;
    }
}
