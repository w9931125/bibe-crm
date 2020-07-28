package com.bibe.crm.dao;
import org.apache.ibatis.annotations.Param;

import com.bibe.crm.entity.po.Roles;import java.util.List;

public interface RolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Roles record);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);

    List<Roles> list();

    //设置录入次数
    int updateNumberById(@Param("updatedNumber")Integer updatedNumber,@Param("id")Integer id);

}