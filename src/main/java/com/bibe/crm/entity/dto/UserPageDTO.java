package com.bibe.crm.entity.dto;

import com.bibe.crm.common.base.BasePage;
import lombok.Data;

@Data
public class UserPageDTO extends BasePage {

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String phone;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 0 启用 1禁止
     */
    private Integer status;
}
