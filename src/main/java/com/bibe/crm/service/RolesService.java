package com.bibe.crm.service;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.UserMapper;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.utils.PinyinUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.Roles;
import com.bibe.crm.dao.RolesMapper;

import java.util.List;

@Service
public class RolesService {

    @Resource
    private RolesMapper rolesMapper;

    @Resource
    private UserMapper userMapper;


    public RespVO delete(Integer id) {

        Integer i = userMapper.selectCountByRoleId(id);
        if (i > 0) {
            return RespVO.fail(ExceptionTypeEnum.ROLES_COUNT_ERROR);
        }
        rolesMapper.deleteByPrimaryKey(id);
        return RespVO.ofSuccess();
    }


    public int add(Roles record) {
        String pinYinHeadChar = PinyinUtils.getPinYinHeadChar(record.getName());
        record.setCode(pinYinHeadChar);
        return rolesMapper.insertSelective(record);
    }


    public Roles show(Integer id) {
        return rolesMapper.selectByPrimaryKey(id);
    }


    public int update(Roles record) {
        String pinYinHeadChar = PinyinUtils.getPinYinHeadChar(record.getName());
        record.setCode(pinYinHeadChar);
        return rolesMapper.updateByPrimaryKeySelective(record);
    }

    public List<Roles> list() {
        return rolesMapper.list();
    }

    public int deleteByPrimaryKey(Integer id) {
        return rolesMapper.deleteByPrimaryKey(id);
    }

    public int insert(Roles record) {
        return rolesMapper.insert(record);
    }

    public int insertSelective(Roles record) {
        return rolesMapper.insertSelective(record);
    }

    public Roles selectByPrimaryKey(Integer id) {
        return rolesMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Roles record) {
        return rolesMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Roles record) {
        return rolesMapper.updateByPrimaryKey(record);
    }
}





