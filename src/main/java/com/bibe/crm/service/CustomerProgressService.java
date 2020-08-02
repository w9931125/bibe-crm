package com.bibe.crm.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.CustomerProgress;
import com.bibe.crm.dao.CustomerProgressMapper;

@Service
public class CustomerProgressService {

    @Resource
    private CustomerProgressMapper customerProgressMapper;


    public int deleteByPrimaryKey(Integer id) {
        return customerProgressMapper.deleteByPrimaryKey(id);
    }


    public int insert(CustomerProgress record) {
        return customerProgressMapper.insert(record);
    }


    public int insertSelective(CustomerProgress record) {
        return customerProgressMapper.insertSelective(record);
    }


    public CustomerProgress selectByPrimaryKey(Integer id) {
        return customerProgressMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(CustomerProgress record) {
        return customerProgressMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(CustomerProgress record) {
        return customerProgressMapper.updateByPrimaryKey(record);
    }

}

