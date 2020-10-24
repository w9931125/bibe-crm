package com.bibe.crm.service;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.*;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.utils.PinyinUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.Roles;

import java.util.List;

@Service
public class RolesService {

    @Resource
    private RolesMapper rolesMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RolesPermissionRelationMapper rolesPermissionRelationMapper;

    @Resource
    private RolesDepartmentRelationMapper rolesDepartmentRelationMapper;

    @Resource
    private RolesCustomerGroupRelationMapper rolesCustomerGroupRelationMapper;

    public RespVO delete(Integer id) {

        Integer i = userMapper.selectCountByRoleId(id);
        if (i > 0) {
            return RespVO.fail(ExceptionTypeEnum.ROLES_COUNT_ERROR);
        }
        rolesMapper.deleteByPrimaryKey(id);
        rolesPermissionRelationMapper.deleteByRoleId(id);
        rolesDepartmentRelationMapper.deleteByRoleId(id);
        rolesCustomerGroupRelationMapper.deleteByRoleId(id);
        return RespVO.ofSuccess();
    }


    public RespVO add(Roles record) {
        Roles allByName = rolesMapper.findAllByName(record.getName());
        if (allByName!=null){
            return RespVO.fail(ExceptionTypeEnum.ROLES_UPDATE_NAME_ERROR);
        }
        String pinYinHeadChar = PinyinUtils.getPinYinHeadChar(record.getName());
        record.setCode(pinYinHeadChar);
        rolesMapper.insertSelective(record);
        return  RespVO.ofSuccess();
    }


    public Roles show(Integer id) {
        return rolesMapper.selectByPrimaryKey(id);
    }


    public RespVO update(Roles record) {
        Roles allByName = rolesMapper.findAllByName(record.getName());
        if (allByName!=null&&!allByName.getId().equals(record.getId())){
            return RespVO.fail(ExceptionTypeEnum.ROLES_UPDATE_NAME_ERROR);
        }
        String pinYinHeadChar = PinyinUtils.getPinYinHeadChar(record.getName());
        record.setCode(pinYinHeadChar);
        rolesMapper.updateByPrimaryKeySelective(record);
        return RespVO.ofSuccess();
    }

    public List<Roles> list() {
        return rolesMapper.list();
    }
}





