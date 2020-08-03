package com.bibe.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.dao.CustomerContactMapper;
import com.bibe.crm.entity.dto.CustomerDTO;
import com.bibe.crm.entity.dto.FindCustomerDTO;
import com.bibe.crm.entity.po.CustomerContact;
import com.bibe.crm.entity.vo.RespVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.Customer;
import com.bibe.crm.dao.CustomerMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerContactMapper customerContactMapper;


    public int delete(Integer[] ids) {
        return customerMapper.updateStatusByIdIn(ids);
    }


    public RespVO add(CustomerDTO record) {
        Customer customer = new Customer();

        BeanUtils.copyProperties(record, customer);

        //新增客户
        customerMapper.insert(record);

        //新增联系人
        CustomerContact customerContact = record.getCustomerContact();
        customerContactMapper.insertSelective(customerContact);
        return RespVO.ofSuccess();
    }

    public RespVO show(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Customer customer = customerMapper.selectByPrimaryKey(id);
        CustomerContact customerContact = customerContactMapper.selectByPrimaryKey(customer.getId());
        map.put("customer", customer);
        map.put("customerContact", customerContact);
        return RespVO.ofSuccess(map);
    }


    public RespVO update(CustomerDTO record, List<Integer> ids) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(record, customer);
        customerMapper.updateByIdIn(customer, ids);

        //联系人
        CustomerContact customerContact = record.getCustomerContact();
        customerContactMapper.updateByCustomerIdin(customerContact,ids);
        return RespVO.ofSuccess();
    }


    public RespVO pageList(FindCustomerDTO dto, Page page) {
        IPage<Map<String, Object>> pageList = customerMapper.pageList(dto,page);
        return RespVO.ofSuccess(pageList);
    }
}





