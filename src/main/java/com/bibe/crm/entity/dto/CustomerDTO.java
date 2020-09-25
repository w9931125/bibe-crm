package com.bibe.crm.entity.dto;

import com.bibe.crm.entity.po.Customer;
import com.bibe.crm.entity.po.CustomerContact;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO extends Customer {
    /**
     * 联系人相关
     */
    private CustomerContact customerContact;

    /**
     * 录入时间
     */
    private String  createDate;

    private  List<Integer> ids;


    /**
     * 批量修改标示 0为普通修改 1批量修改
     */
    private Integer updateFlag=0;
}
