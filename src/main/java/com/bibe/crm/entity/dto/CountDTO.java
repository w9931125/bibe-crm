package com.bibe.crm.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class CountDTO {


    /**
     * 用户id组
     */
    private List<Integer> userIds;

    /**
     * 年 yyyy
     */
    private String year;

    /**
     * 月 yyyy-MM
     */
    private String month;

    /**
     * flag 1新增客户数 2联系跟进次数 3跟进客户数
     */
    private Integer flag;
}
