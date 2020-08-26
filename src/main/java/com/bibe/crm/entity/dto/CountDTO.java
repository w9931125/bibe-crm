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

    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 1按城市 2行业 3客户意向度 4客户类别
     */
    private Integer  sort;
}
