package com.bibe.crm.entity.dto;

import com.bibe.crm.common.base.BasePage;
import lombok.Data;

import java.util.List;

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
    private List<Integer> deptIds;

    /**
     * 0 启用 1禁止
     */
    private Integer status;
}
