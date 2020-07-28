package com.bibe.crm.service;

import com.bibe.crm.dao.CustomerContactMapper;
import com.bibe.crm.entity.dto.CustomerDTO;
import com.bibe.crm.entity.po.CustomerContact;
import com.bibe.crm.entity.vo.RespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.Customer;
import com.bibe.crm.dao.CustomerMapper;

import java.util.List;

@Service
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerContactMapper customerContactMapper;


    public int deleteByPrimaryKey(Integer id) {
        return customerMapper.deleteByPrimaryKey(id);
    }


    public RespVO add(CustomerDTO record) {
        Customer customer=new Customer();

        BeanUtils.copyProperties(record,customer);

        //新增客户
        customerMapper.insert(record);

        //新增联系人
        List<CustomerContact> contactList = record.getContactList();
        customerContactMapper.insertList(contactList);
        return RespVO.ofSuccess();
    }

    public Customer selectByPrimaryKey(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(Customer record) {
        return customerMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Customer record) {
        return customerMapper.updateByPrimaryKey(record);
    }

}



