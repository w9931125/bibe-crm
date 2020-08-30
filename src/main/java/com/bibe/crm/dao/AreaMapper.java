package com.bibe.crm.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.bibe.crm.entity.po.Area;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

    List<Area> list(@Param("pid")Integer pid);

    Integer findIdByName(@Param("name")String name);
}