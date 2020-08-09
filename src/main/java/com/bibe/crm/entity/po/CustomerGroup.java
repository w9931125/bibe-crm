package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * 公客分组
 */
@Data
public class CustomerGroup {
    private Integer id;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 自动转交状态:0未开启 1开启
     */
    private Integer status;

    /**
     * 转交天数--到期自动释放
     */
    private Integer endDay;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}