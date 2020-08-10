package com.bibe.crm.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerMoveDTO {


    /**
     * 用户id
     */
    private Integer userId;


    /**
     * 客户id组
     */
    private  List<Integer> ids;


    /**
     * 分组id
     */
    private Integer groupId;
}
