package com.bibe.crm.service;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.CustomerGroupDepartmentRelationMapper;
import com.bibe.crm.dao.RolesDepartmentRelationMapper;
import com.bibe.crm.dao.UserMapper;
import com.bibe.crm.entity.po.RolesDepartmentRelation;
import com.bibe.crm.entity.po.User;
import com.bibe.crm.entity.vo.DeptNameVO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.entity.vo.TreeData;
import com.bibe.crm.utils.DepartmentUtil;
import com.bibe.crm.utils.ShiroUtils;
import com.bibe.crm.utils.TreeUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.Department;
import com.bibe.crm.dao.DepartmentMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class DepartmentService{

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentUtil departmentUtil;

    @Resource
    private RolesDepartmentRelationMapper rolesDepartmentRelationMapper;
    @Resource
    private CustomerGroupDepartmentRelationMapper customerGroupDepartmentRelationMapper;
    
    public RespVO deleteByPrimaryKey(Integer id) {
        int i = departmentMapper.selectCountByParentId(id);
        if (i>0){
            return RespVO.fail(ExceptionTypeEnum.DEPT_COUNT_ERROR);
        }
        int t = userMapper.selectCountByDeptId(id);
        if (t>0){
            return RespVO.fail(ExceptionTypeEnum.DEPT_USER_COUNT_ERROR);
        }
        departmentMapper.deleteByPrimaryKey(id);
        rolesDepartmentRelationMapper.deleteByDeptId(id);
        customerGroupDepartmentRelationMapper.deleteByDeptId(id);
        return  RespVO.ofSuccess();
    }

    
    public RespVO insert(Department record) {
        Department allByName = departmentMapper.findAllByName(record.getName());
        if (allByName!=null){
            return RespVO.fail(ExceptionTypeEnum.DEPT_NAME_ERROR);
        }
        record.setCreateTime(new Date());
        departmentMapper.insert(record);
        return  RespVO.ofSuccess();
    }
    
    public RespVO show(Integer id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
        return RespVO.ofSuccess(department);
    }

    
    public RespVO update(Department record) {
        Department allByName = departmentMapper.findAllByName(record.getName());
        if (allByName!=null&&!allByName.getId().equals(record.getId())){
            return RespVO.fail(ExceptionTypeEnum.DEPT_NAME_ERROR);
        }
        record.setUpdateTime(new Date());
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


    public RespVO  deptNameList(String name,List<Integer> deptId) {
//        List<Integer> deptIds=new ArrayList<>();
//        User userInfo = ShiroUtils.getUserInfo();
//        //客户资料查询权限
//        List<RolesDepartmentRelation> rolesDepartmentRelationList = rolesDepartmentRelationMapper.selectAllByRoleIdAndType(userInfo.getRoleId(), 0);
//        if (rolesDepartmentRelationList.size()>1) {
//            rolesDepartmentRelationList.forEach(i -> deptIds.add(i.getDeptId()));
//        }
//
//        List<Integer> list = departmentUtil.getChildDeptId(deptId);
//        //过滤掉没有权限的值
//        list.removeAll(deptIds);
        List<DeptNameVO> deptNameVOS = userMapper.selectNameDeptName(name, deptId);
        return RespVO.ofSuccess(deptNameVOS);
    }

}
