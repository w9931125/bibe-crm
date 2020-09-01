package com.bibe.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.*;
import com.bibe.crm.entity.dto.CountDTO;
import com.bibe.crm.entity.dto.CustomerDTO;
import com.bibe.crm.entity.dto.FindCustomerDTO;
import com.bibe.crm.entity.dto.FindCustomerGroupDTO;
import com.bibe.crm.entity.po.*;
import com.bibe.crm.entity.vo.*;
import com.bibe.crm.utils.DateUtils;
import com.bibe.crm.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
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

    @Resource
    private FilesMapper filesMapper;


    public int delete(Integer[] ids) {
        return customerMapper.updateStatusByIdIn(ids);
    }


    /**
     * 按时间统计客户
     * @param countDTO
     * @return
     */
    public RespVO findCountByDate(CountDTO countDTO){
        return RespVO.ofSuccess(getCountByDate(countDTO));
    }

    public List<CountDayVO> getCountByDate(CountDTO countDTO){
        if (countDTO.getYear()==null&&countDTO.getMonth()==null){
            log.info("全部统计>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return customerMapper.countCustomerByYear(countDTO.getUserIds(),countDTO.getFlag());
        }else if (countDTO.getYear()!=null){
            log.info("按月统计>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return customerMapper.countCustomerByMonth(countDTO.getUserIds(),countDTO.getYear(),countDTO.getFlag());
        }else if (countDTO.getMonth()!=null){
            log.info("按日统计>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return customerMapper.countCustomerByDay(countDTO.getUserIds(),countDTO.getMonth(),countDTO.getFlag());
        }
        return null;
    }


    /**
     * 按分类统计
     * @param countDTO
     * @return
     */
    public RespVO findCountBySort(CountDTO countDTO){
        return RespVO.ofSuccess(getCountBySort(countDTO));
    }


    public List<CountSortVO> getCountBySort(CountDTO countDTO){
        //     * 1按城市 2行业 3客户意向度 4客户类别
        if (countDTO.getSort().equals(1)){
            log.info("按城市统计>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return customerMapper.countCustomerBySort1(countDTO);
        }else if (countDTO.getSort().equals(2)){
            log.info("按行业统计>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return customerMapper.countCustomerBySort2(countDTO);
        }else if (countDTO.getSort().equals(3)){
            log.info("按客户意向度>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return customerMapper.countCustomerBySort3(countDTO);
        }else if (countDTO.getSort().equals(4)){
            log.info("按4客户类别>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return customerMapper.countCustomerBySort4(countDTO);
        }
        return null;
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
        //customer.setCreateTime(new Date());
        customerMapper.insertSelective(customer);
        //新增联系人
        CustomerContact customerContact = record.getCustomerContact();
        customerContact.setCustomerId(customer.getId());
        customerContact.setCreateTime(new Date());
        int i = customerContactMapper.insertSelective(customerContact);
        //减少录入次数
        if (i > 1 && !userInfo.getNumber().equals(-1)) {
            userMapper.updateNumberById(userInfo.getId());
        }
        return RespVO.ofSuccess();
    }

    public RespVO edit(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String,Object>> files=new ArrayList<>();
        Customer customer = customerMapper.selectByPrimaryKey(id);
        if (customer.getFilesId()!=null){
            String[] fileIds = customer.getFilesId().split(",");
            files = filesMapper.findFileByIds(fileIds);
        }
        CustomerContact customerContact = customerContactMapper.findAllById(customer.getId());
        map.put("customer", customer);
        map.put("customerContact", customerContact);
        map.put("files",files);
        return RespVO.ofSuccess(map);
    }


    public RespVO update(CustomerDTO record) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(record, customer);
        try {
            Date date = DateUtils.getDateStringToDate(record.getCreateDate());
            customer.setCreateTime(date);
        } catch (Exception e) {
            log.info("客户添加出现异常{}",e);
        }
        customerMapper.updateByIdIn(customer,record.getIds());
        /*        //联系人
        CustomerContact customerContact = record.getCustomerContact();
        customerContactMapper.updateByCustomerIdin(customerContact, ids);*/
        return RespVO.ofSuccess();
    }


    public IPage<CustomerVO> pageList(FindCustomerDTO dto, Page page) {
/*        if (dto.getUserId()==null||dto.getDeptId()==null){
            return RespVO.fail(ExceptionTypeEnum.SELECT_CUSTOMER_ERROR);
        }*/
        List<Integer> userIds = dto.getUserIds();
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
            System.out.printf("看下id"+i.getId().toString());
            CustomerContact customerContact = customerContactMapper.findAllByCustomerId(i.getId());
            if (customerContact!=null){
                if (customerContact.getPhone()!=null){
                    i.setPhone(customerContact.getPhone());
                }
            }
        });
        pageList.setRecords(records);
        return pageList;
    }



    public IPage<CustomerVO> myPageList(FindCustomerDTO dto, Page page) {
        Integer id = ShiroUtils.getUserInfo().getId();
        IPage<CustomerVO> pageList = customerMapper.myPageList(dto, page,id);
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
        return pageList;
    }


    /**
     * 公客分页列表
     * @param dto
     * @param page
     * @return
     */
    public IPage<PublicCustomerVO>  customerGroupPageList(FindCustomerGroupDTO dto, Page page) {
/*        if (dto.getUserId()==null||dto.getDeptId()==null){
            return RespVO.fail(ExceptionTypeEnum.SELECT_CUSTOMER_ERROR);
        }*/
        IPage<PublicCustomerVO> pageList = customerMapper.customerGroupPageList(dto, page);
        List<PublicCustomerVO> records = pageList.getRecords();
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
        return pageList;
    }

    public RespVO move(Integer userId,Integer groupId,List<Integer> ids){
        customerMapper.updateUserIdAndGroupIdByIdIn(userId,groupId,ids);
        return RespVO.ofSuccess();
    }



    public RespVO findLikeName(String name){
        List<Map<String, Object>> likeName = customerMapper.findLikeName(name);
        return RespVO.ofSuccess(likeName);
    }

}







