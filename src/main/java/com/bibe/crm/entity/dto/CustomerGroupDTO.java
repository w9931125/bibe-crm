package com.bibe.crm.entity.dto;


import com.bibe.crm.entity.po.CustomerGroup;
import com.bibe.crm.entity.po.CustomerGroupDepartmentRelation;
import lombok.Data;

import java.util.List;

@Data
public class CustomerGroupDTO  extends CustomerGroup {
    private List<CustomerGroupDepartmentRelation> groupList;
}
