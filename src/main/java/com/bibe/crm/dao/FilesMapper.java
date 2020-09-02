package com.bibe.crm.dao;

import org.apache.ibatis.annotations.Param;

import com.bibe.crm.entity.po.Files;

import java.util.List;
import java.util.Map;

public interface FilesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Files record);

    int insertSelective(Files record);

    Files selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKey(Files record);

    List<Map<String,Object>> findFileByIds(@Param("ids")String [] ids);
}