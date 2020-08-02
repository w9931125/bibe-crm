package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * 客户进度表（联系跟进）
 */
@Data
public class CustomerProgress {
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 联系人
     */
    private String name;

    /**
     * 联系方式0:电话,1:QQ,2:微信,3:登门拜访,4:邮件
     */
    private String contactType;

    /**
     * 职务
     */
    private String position;

    /**
     * 沟通时长/分钟
     */
    private Integer speakTime;

    /**
     * 跟进结果
     */
    private String remarks;

    /**
     * 客户满意度,0成交客户,1A+,2A-,3B+,4B-,5C+,6C-,7D,8E
     */
    private Integer satisfied;

    /**
     * 创建人id
     */
    private Integer userId;

    /**
     * 下次跟进时间
     */
    private Date nextTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}