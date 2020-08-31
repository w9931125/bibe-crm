package com.bibe.crm.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProgressVO implements Serializable {

    @Excel(name = "编号", orderNum = "0", width = 15)
    private Integer id;
    /**
     * 客户名称
     */
    @Excel(name = "客户名称", orderNum = "1", width = 15)
    private String customerName;

    /**
     *联系人名称
     */
    @Excel(name = "联系人名称", orderNum = "2", width = 15)
    private String contactName;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分组id
     */
    private Integer groupId;


    /**
     * 沟通时长/分钟
     */
    @Excel(name = "沟通时长/分钟", orderNum = "3", width = 15)
    private Integer speakTime;


    /**
     * 联系方式0:电话,1:QQ,2:微信,3:登门拜访,4:邮件
     */
    @Excel(name = "联系方式", replace = { "电话_0", "QQ_1","微信_2","登门拜访_3","邮件_4","无_null" },orderNum = "4",width = 15)
    private String contactType;

    /**
     * 跟进时间
     */
    @Excel(name = "跟进时间", orderNum = "5", width = 15 ,databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd" )
    private String progressTime;

    /**
     * 跟进结果
     */
    @Excel(name = "跟进结果", orderNum = "6", width = 15)
    private String remarks;


    /**
     * 客户满意度,0成交客户,1A+,2A-,3B+,4B-,5C+,6C-,7D,8E
     */
    @Excel(name = "意向度", replace = { "成交客户_0", "A+_1","A-_2","B+_3","B-_4","C+_5","C-_6","D_7","E_8","无_null" },orderNum = "7",width = 15)
    private Integer satisfied;

    /**
     * 评论内容
     */
    private List<Map<String,Object>> commentInfo;

    @Excel(name = "跟进人", orderNum = "8", width = 15)
    private String userName;
}
