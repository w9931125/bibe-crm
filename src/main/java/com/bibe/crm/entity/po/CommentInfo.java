package com.bibe.crm.entity.po;

import java.util.Date;
import lombok.Data;

/**
    * 跟进评论内容
    */
@Data
public class CommentInfo {
    private Integer id;

    /**
    * 评论内容
    */
    private String remarks;

    /**
    * 跟进id
    */
    private Integer progressId;

    /**
    * 评论人id
    */
    private Integer userId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}