package com.bibe.crm.service;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.UserMapper;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.entity.vo.TreeData;
import com.bibe.crm.utils.TreeUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.Department;
import com.bibe.crm.dao.DepartmentMapper;

import java.util.List;

@Service
public class DepartmentService{

    @Resource
    private DepartmentMapper departmentMapper;


    @Resource
    private UserMapper userMapper;
    
    public RespVO deleteByPrimaryKey(Integer id) {
        int i = departmentMapper.selectCountByParentId(id);
        if (i>1){
            return RespVO.fail(ExceptionTypeEnum.DEPT_COUNT_ERROR);
        }
        int t = userMapper.selectCountByDeptId(id);
        if (t>1){
            return RespVO.fail(ExceptionTypeEnum.DEPT_USER_COUNT_ERROR);
        }
        departmentMapper.deleteByPrimaryKey(id);
        return  RespVO.ofSuccess();
    }

    
    public RespVO insert(Department record) {
        departmentMapper.insert(record);
        return  RespVO.ofSuccess();
    }
    
    public RespVO show(Integer id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
        return RespVO.ofSuccess(department);
    }

    
    public RespVO update(Department record) {
        departmentMapper.updateByPrimaryKeySelective(record);
        return RespVO.ofSuccess();
    }


    public List<Department> list(Integer parentId) {
        List<Department> list = departmentMapper.list(parentId);
        return list;
    }


    public List<TreeData> tree(){
        List<TreeData> tree = departmentMapper.tree();
        return TreeUtil.getTreeList(tree,0);
    }

}
