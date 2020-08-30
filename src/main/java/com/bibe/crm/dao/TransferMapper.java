package com.bibe.crm.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.bibe.crm.entity.po.Transfer;

public interface TransferMapper {
    int insert(Transfer record);

    int insertSelective(Transfer record);

    int insertList(@Param("list")List<Transfer> list);

    int deleteByVersion(@Param("version")String version);

    List<Transfer> findAllByVersion(@Param("version")String version);


}