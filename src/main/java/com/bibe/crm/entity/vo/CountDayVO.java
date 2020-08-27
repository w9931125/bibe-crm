package com.bibe.crm.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 统计
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CountDayVO implements Serializable {
    @Excel(name = "日期", orderNum = "0", width = 15)
    private String date;

    @Excel(name = "总数", orderNum = "1", width = 15)
    private Integer count;
}
