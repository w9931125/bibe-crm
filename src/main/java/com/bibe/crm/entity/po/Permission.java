package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * 权限表
 */
@Data
public class Permission {
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 启用：0 禁用 1
     */
    private Integer status;

    /**
     * 0菜单 1功能
     */
    private Integer type;

    /**
     * 父级权限
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}