package com.bibe.crm.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProgressVO {

    private Integer id;

    /**
     * 沟通时长/分钟
     */
    private Integer speakTime;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     *联系人名称
     */
    private String contactName;

    /**
     * 联系方式0:电话,1:QQ,2:微信,3:登门拜访,4:邮件
     */
    private String contactType;

    /**
     * 跟进时间
     */
    private String progressTime;

    /**
     * 跟进结果
     */
    private String remarks;


    /**
     * 客户满意度,0成交客户,1A+,2A-,3B+,4B-,5C+,6C-,7D,8E
     */
    private Integer satisfied;

    /**
     * 评论内容
     */
    private List<Map<String,Object>> commentInfo;


    private String userName;
}
