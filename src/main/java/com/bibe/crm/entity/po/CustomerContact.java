package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * 客户联系人信息
 */
@Data
public class CustomerContact {
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

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
    private String name;

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
     * 联系人类型：0次要 1主要
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}