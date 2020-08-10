package com.bibe.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.*;
import com.bibe.crm.entity.dto.CustomerDTO;
import com.bibe.crm.entity.dto.FindCustomerDTO;
import com.bibe.crm.entity.dto.FindCustomerGroupDTO;
import com.bibe.crm.entity.po.*;
import com.bibe.crm.entity.vo.CustomerVO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.utils.DateUtils;
import com.bibe.crm.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.*;

@Service
@Slf4j
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
        try {
            Date date = DateUtils.getDateStringToDate(record.getCreateDate());
            customer.setCreateTime(date);
        } catch (Exception e) {
            log.error("客户添加出现异常{}",e);
        }
        //新增客户
        customerMapper.insertSelective(customer);
        //新增联系人
        CustomerContact customerContact = record.getCustomerContact();
        customerContact.setCustomerId(customer.getId());
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
        try {
            Date date = DateUtils.getDateStringToDate(record.getCreateDate());
            customer.setCreateTime(date);
        } catch (Exception e) {
            log.info("客户添加出现异常{}",e);
        }
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
            //联系跟进
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
            //主要联系人
            CustomerContact customerContact = customerContactMapper.findAllByCustomerId(i.getId());
            if (customerContact!=null){
                if (customerContact.getPhone()!=null){
                    i.setPhone(customerContact.getPhone());
                }
            }
        });
        pageList.setRecords(records);
        return RespVO.ofSuccess(pageList);
    }


    /**
     * 公客分页列表
     * @param dto
     * @param page
     * @return
     */
    public RespVO customerGroupPageList(FindCustomerGroupDTO dto, Page page) {
/*        if (dto.getUserId()==null||dto.getDeptId()==null){
            return RespVO.fail(ExceptionTypeEnum.SELECT_CUSTOMER_ERROR);
        }*/
        IPage<CustomerVO> pageList = customerMapper.customerGroupPageList(dto, page);
        List<CustomerVO> records = pageList.getRecords();
        records.forEach(i -> {
            //联系跟进
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
            //主要联系人
            CustomerContact customerContact = customerContactMapper.findAllByCustomerId(i.getId());
            if (customerContact!=null){
                if (customerContact.getPhone()!=null){
                    i.setPhone(customerContact.getPhone());
                }
            }
        });
        pageList.setRecords(records);
        return RespVO.ofSuccess(pageList);
    }

    public RespVO move(Integer userId,Integer groupId,List<Integer> ids){
        customerMapper.updateUserIdAndGroupIdByIdIn(userId,groupId,ids);
        return RespVO.ofSuccess();
    }
}







