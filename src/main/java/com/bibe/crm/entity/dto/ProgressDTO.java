package com.bibe.crm.entity.dto;

import com.bibe.crm.common.base.BasePage;
import lombok.Data;

@Data
public class ProgressDTO extends BasePage {

    /**
     * 用户id(与部门ID取一个)
     */
    private Integer userId;

    /**
     * 部门id(与用户ID取一个)
     */
    private Integer deptId;

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
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;
}
