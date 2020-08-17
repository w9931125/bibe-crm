package com.bibe.crm.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.entity.dto.ProgressDTO;
import com.bibe.crm.entity.po.CustomerProgress;
import com.bibe.crm.entity.vo.ProgressVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerProgress record);

    int insertSelective(CustomerProgress record);

    CustomerProgress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerProgress record);

    int updateByPrimaryKey(CustomerProgress record);

    /**
     * 最新跟进
     *
     * @param customerId
     * @return
     */
    CustomerProgress findNewInfo(@Param("customerId") Integer customerId);

    /**
     * 详情
     * @param id
     * @return
     */
    Map<String,Object> show(Integer id);


    IPage<ProgressVO> pageList(@Param("dto")ProgressDTO dto, Page page, @Param("userIds") List<Integer> userIds);

    List<ProgressVO> list(@Param("customerId") Integer customerId);
}