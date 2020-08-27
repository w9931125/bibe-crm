package com.bibe.crm.service;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.CustomerGroupDepartmentRelationMapper;
import com.bibe.crm.entity.dto.CustomerGroupDTO;
import com.bibe.crm.entity.po.CustomerGroupDepartmentRelation;
import com.bibe.crm.entity.vo.CustomerGroupVO;
import com.bibe.crm.entity.vo.RespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.CustomerGroup;
import com.bibe.crm.dao.CustomerGroupMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerGroupService{

    @Resource
    private CustomerGroupMapper customerGroupMapper;

    @Resource
    private CustomerGroupDepartmentRelationMapper  customerGroupDepartmentRelationMapper;


    public RespVO delete(Integer id) {
        int count = customerGroupDepartmentRelationMapper.findCountByCustomerGroupId(id);
        if (count>0){
            return RespVO.fail(ExceptionTypeEnum.DELETE_CUSTOMER_GROUP_ERROR);
        }
        customerGroupMapper.deleteByPrimaryKey(id);
        return RespVO.ofSuccess();
    }


    public RespVO add(CustomerGroupDTO record) {
        CustomerGroup customerGroup=new CustomerGroup();

        BeanUtils.copyProperties(record,customerGroup);
        customerGroup.setCreateTime(new Date());
        customerGroupMapper.insertSelective(customerGroup);
        //分组关联部门
        List<CustomerGroupDepartmentRelation> groupList = record.getGroupList();
        groupList.forEach(i->i.setCustomerGroupId(customerGroup.getId()));
        customerGroupDepartmentRelationMapper.insertList(groupList);
        return  RespVO.ofSuccess();
    }


    public RespVO show(Integer id) {
        Map<String,Object> map=new HashMap<>();
        CustomerGroup customerGroup = customerGroupMapper.selectByPrimaryKey(id);
        List<Map<String, Object>> allByCustomerGroupId = customerGroupDepartmentRelationMapper.findAllByCustomerGroupId(id);
        map.put("customerGroup",customerGroup);
        map.put("depts",allByCustomerGroupId);
        return RespVO.ofSuccess(map);
    }


    public RespVO update(CustomerGroupDTO record) {
        CustomerGroup customerGroup=new CustomerGroup();
        BeanUtils.copyProperties(record,customerGroup);

        //删除之前的重新
        customerGroupDepartmentRelationMapper.deleteByCustomerGroupId(record.getId());
        //分组关联部门
        List<CustomerGroupDepartmentRelation> groupList = record.getGroupList();
        if (groupList.size()>0){
            groupList.forEach(i->i.setCustomerGroupId(record.getId()));
            customerGroupDepartmentRelationMapper.insertList(groupList);
            customerGroup.setUpdateTime(new Date());
            customerGroupMapper.updateByPrimaryKeySelective(record);
        }
        return  RespVO.ofSuccess();
    }

    
    public RespVO list() {
        List<CustomerGroupVO> list = customerGroupMapper.list();
        list.forEach(i->{
            String deptNames = customerGroupDepartmentRelationMapper.findDeptNamesByGroupId(i.getId());
            i.setDeptNames(deptNames);
        });
        return RespVO.ofSuccess(list);
    }

}
