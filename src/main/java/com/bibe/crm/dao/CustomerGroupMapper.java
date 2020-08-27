package com.bibe.crm.dao;
import java.util.Collection;
import com.bibe.crm.entity.vo.CustomerGroupVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.bibe.crm.entity.po.CustomerGroup;

public interface CustomerGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerGroup record);

    int insertSelective(CustomerGroup record);

    CustomerGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerGroup record);

    int updateByPrimaryKey(CustomerGroup record);

    List<CustomerGroupVO> list();

    List<Map<String,Object>> mapList();

    List<Map<String,Object>> findAllByIdIn(@Param("idCollection")Collection<Integer> idCollection);

    List<CustomerGroup> groupList();
}