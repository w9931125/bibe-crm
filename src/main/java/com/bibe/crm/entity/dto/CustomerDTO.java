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
}
