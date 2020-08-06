package com.bibe.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.*;
import com.bibe.crm.entity.dto.CustomerDTO;
import com.bibe.crm.entity.dto.FindCustomerDTO;
import com.bibe.crm.entity.po.*;
import com.bibe.crm.entity.vo.CustomerVO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.utils.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerContactMapper customerContactMapper;

    @Resource
    private CustomerProgressMapper customerProgressMapper;

    @Resource
    private UserMapper userMapper;


    public int delete(Integer[] ids) {
        return customerMapper.updateStatusByIdIn(ids);
    }


    public RespVO add(CustomerDTO record) {
        User userInfo = ShiroUtils.getUserInfo();
        if (userInfo.getNumber().equals(0)) {
            return RespVO.fail(ExceptionTypeEnum.USER_NUMBER_ERROR);
        }
        Customer customer = new Customer();

        BeanUtils.copyProperties(record, customer);
        //新增客户
        customerMapper.insert(record);

        //新增联系人
        CustomerContact customerContact = record.getCustomerContact();
        int i = customerContactMapper.insertSelective(customerContact);
        //减少录入次数
        if (i > 1 && !userInfo.getNumber().equals(-1)) {
            userMapper.updateNumberById(userInfo.getId());
        }
        return RespVO.ofSuccess();
    }

    public RespVO edit(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Customer customer = customerMapper.selectByPrimaryKey(id);
        CustomerContact customerContact = customerContactMapper.findAllById(customer.getId());
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
        customerContactMapper.updateByCustomerIdin(customerContact, ids);
        return RespVO.ofSuccess();
    }


    public RespVO pageList(FindCustomerDTO dto, Page page) {
/*        if (dto.getUserId()==null||dto.getDeptId()==null){
            return RespVO.fail(ExceptionTypeEnum.SELECT_CUSTOMER_ERROR);
        }*/
        List<Integer> userIds = new ArrayList<>();
        if (null != dto.getDeptId()) {
            userIds = userMapper.findIdByDeptId(dto.getDeptId());
        }
        IPage<CustomerVO> pageList = customerMapper.pageList(dto, page, userIds);
        List<CustomerVO> records = pageList.getRecords();
        records.forEach(i -> {
            CustomerProgress newInfo = customerProgressMapper.findNewInfo(i.getId());
            if (newInfo!=null){
                if (newInfo.getRemarks()!=null){
                    i.setRemarks(newInfo.getRemarks());
                }
                if (newInfo.getNextTime()!=null){
                    i.setNextTime(newInfo.getNextTime());
                }
                i.setLatsTime(newInfo.getCreateTime());
            }
        });
        pageList.setRecords(records);
        return RespVO.ofSuccess(pageList);
    }
}







