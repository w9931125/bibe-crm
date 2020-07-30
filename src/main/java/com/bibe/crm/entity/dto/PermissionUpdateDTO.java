package com.bibe.crm.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class PermissionUpdateDTO {
    private List<Integer> ids;
    private  Integer status;
    private  Integer roleId;
}
