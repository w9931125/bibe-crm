package com.bibe.crm.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeData {

    private Integer id;

    private Integer pid;

    private String  title;

    private Integer num;

    private Integer level;

    private List<TreeData> children;
}
