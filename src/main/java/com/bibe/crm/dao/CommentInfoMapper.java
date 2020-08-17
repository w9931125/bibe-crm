package com.bibe.crm.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.bibe.crm.entity.po.CommentInfo;

public interface CommentInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentInfo record);

    int insertSelective(CommentInfo record);

    CommentInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentInfo record);

    int updateByPrimaryKey(CommentInfo record);

   List< Map<String,Object> > findAllByProgressId(@Param("progressId")Integer progressId);
}