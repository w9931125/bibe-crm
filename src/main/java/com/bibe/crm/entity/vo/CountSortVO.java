package com.bibe.crm.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分类统计
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CountSortVO implements Serializable {

    private Integer flag;

    @Excel(name = "名称", orderNum = "0", width = 15,replace = {"未分类_null"})
    private String name;

    @Excel(name = "数量", orderNum = "1", width = 15)
    private Integer count;
}
