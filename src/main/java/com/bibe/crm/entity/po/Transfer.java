package com.bibe.crm.entity.po;

import lombok.Data;

/**
 * excel导入数据中转表
 */
@Data
public class Transfer {
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
    private String intention;

    /**
     * 客户类别:0代理商 1直客 2采购方
     */
    private String type;

    /**
     * 0普通 1公客
     */
    private Integer customerType;

    /**
     * 客户地址
     */
    private String address;

    /**
     * 职务
     */
    private String position;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * qq号
     */
    private String qq;

    /**
     * 座机
     */
    private String landline;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 版本号
     */
    private String version;
}