package com.bibe.crm.service;

import com.bibe.crm.dao.*;
import com.bibe.crm.entity.dto.PermissionDTO;
import com.bibe.crm.entity.po.*;
import com.bibe.crm.entity.vo.PermissionVO;
import com.bibe.crm.entity.vo.RespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;

@Service
@Slf4j
public class PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolesCustomerGroupRelationMapper rolesCustomerGroupRelationMapper;

    @Resource
    private RolesDepartmentRelationMapper rolesDepartmentRelationMapper;

    @Resource
    private RolesPermissionRelationMapper rolesPermissionRelationMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RolesMapper rolesMapper;



    public Map<String, String> loadFilterChainDefinitionMap() {
        // 权限控制map
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        Set<String> path = permissionMapper.selectPath();
        path.forEach(i -> {
            filterChainDefinitionMap.put(i, "perms" + "[" + i + "]");
        });
        log.info("所有权限已装载完毕>>>>>>>>>>");
        return filterChainDefinitionMap;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RespVO add(PermissionDTO dto){

        //1客户资料 2联系跟进 3部门员工 4职位权限
        Integer flag = dto.getFlag();

        switch (flag){
            case 1:
                //授权接口
                List<RolesPermissionRelation> permissionList = dto.getPermissionList();

                rolesPermissionRelationMapper.deleteByRoleId(dto.getRoleId(),flag);

                if (permissionList.size()>0){
                    permissionList.forEach(i->i.setType(flag));
                    rolesPermissionRelationMapper.insertList(permissionList);
                }
                //为null说明是指定部门
                if (null==dto.getRolesDepartId()){
                    //授权查看部门
                    List<RolesDepartmentRelation> rolesDepartmentRelationList = dto.getRolesDepartmentRelationList();

                    rolesDepartmentRelationMapper.deleteByRoleId(dto.getRoleId(),0);

                    //全部客户
                    if (rolesDepartmentRelationList.size()>0){
                        rolesDepartmentRelationList.forEach(i->i.setType(0));
                        rolesDepartmentRelationMapper.insertList(rolesDepartmentRelationList);
                    }

                }else {
                    rolesDepartmentRelationMapper.deleteByRoleId(dto.getRoleId(),0);
                    //     部门id -1为全部客户 0为禁止 -2只看自己负责 -3同步客户 -4与自己同一个部门 -5与自己同一个部门及下级部门
                    RolesDepartmentRelation rolesDepartmentRelation=new RolesDepartmentRelation();
                    rolesDepartmentRelation.setType(0);
                    rolesDepartmentRelation.setDeptId(dto.getRolesDepartId());
                    rolesDepartmentRelation.setRoleId(dto.getRoleId());
                    rolesDepartmentRelationMapper.insert(rolesDepartmentRelation);
                }
                //授权公客
                List<RolesCustomerGroupRelation> groupRelationList = dto.getGroupRelationList();

                rolesCustomerGroupRelationMapper.deleteByRoleId(dto.getRoleId());

                if (groupRelationList.size()>0){
                    rolesCustomerGroupRelationMapper.insertList(groupRelationList);
                }
                //设置录入人数
                rolesMapper.updateNumberById(dto.getNumber(),dto.getRoleId());
                userMapper.updateNumberByRoleId(dto.getNumber(),dto.getRoleId());
                break;
            case 2:
                //授权接口
                List<RolesPermissionRelation> permissionRelationList = dto.getPermissionList();

                rolesPermissionRelationMapper.deleteByRoleId(dto.getRoleId(),flag);

                if (permissionRelationList.size()>0){
                    permissionRelationList.forEach(i->i.setType(flag));
                    rolesPermissionRelationMapper.insertList(permissionRelationList);
                }
                //为null说明是指定部门
                if (null==dto.getRolesDepartId()){
                    //授权查看部门
                    List<RolesDepartmentRelation> rolesDepartmentRelationList = dto.getRolesDepartmentRelationList();

                    rolesDepartmentRelationMapper.deleteByRoleId(dto.getRoleId(),1);

                    if (rolesDepartmentRelationList.size()>0){
                        rolesDepartmentRelationList.forEach(i->i.setType(1));
                        rolesDepartmentRelationMapper.insertList(rolesDepartmentRelationList);
                    }
                }else {
                    rolesDepartmentRelationMapper.deleteByRoleId(dto.getRoleId(),1);
                    //     部门id -1为全部客户 0为禁止 -2只看自己负责 -3同步客户 -4与自己同一个部门 -5与自己同一个部门及下级部门
                    RolesDepartmentRelation rolesDepartmentRelation=new RolesDepartmentRelation();
                    rolesDepartmentRelation.setType(1);
                    rolesDepartmentRelation.setDeptId(dto.getRolesDepartId());
                    rolesDepartmentRelation.setRoleId(dto.getRoleId());
                    rolesDepartmentRelationMapper.insert(rolesDepartmentRelation);
                }
                break;
            case 3:
                rolesPermissionRelationMapper.deleteByRoleId(dto.getRoleId(),flag);
                List<RolesPermissionRelation> permissionList1 = dto.getPermissionList();
                if (permissionList1.size()>0){
                    permissionList1.forEach(i->i.setType(flag));
                    rolesPermissionRelationMapper.insertList(permissionList1);
                }
                break;
            case 4:
                rolesPermissionRelationMapper.deleteByRoleId(dto.getRoleId(),flag);
                List<RolesPermissionRelation> permissionList2 = dto.getPermissionList();
                if (permissionList2.size()>0){
                    permissionList2.forEach(i->i.setType(flag));
                    rolesPermissionRelationMapper.insertList(permissionList2);
                }
                break;
        }
        return RespVO.ofSuccess();
    }

    /**
     * 获取角色拥有的权限
     * @param roleId
     * @param parentId
     * @return
     */
    public RespVO show(Integer roleId,Integer parentId){
        PermissionVO vo=new PermissionVO();
        //* 1设置职位与权限 2部门与员工 3客户资料 4联系跟进
        switch (parentId){
            //获取主要四个菜单
            case 0:
                vo.setPermissionList(permissionMapper.selectPermissionByRose(parentId, 0, roleId));
                return RespVO.ofSuccess(vo);
            case 1:
                vo.setPermissionList(permissionMapper.selectPermissionByRose(parentId, 1, roleId));
                return RespVO.ofSuccess(vo);
            case 2:
                vo.setPermissionList(permissionMapper.selectPermissionByRose(parentId, 1, roleId));
                return RespVO.ofSuccess(vo);
            case 3:
                //查看部门权限
                List<RolesDepartmentRelation> rolesDepartmentRelations = rolesDepartmentRelationMapper.selectAllByRoleIdAndType(roleId, 0);
                //录入次数
                Roles roles = rolesMapper.selectByPrimaryKey(roleId);
                //功能权限
                List<Permission> permissions = permissionMapper.selectPermissionByRose(parentId, 1, roleId);
                //公客权限
                List<RolesCustomerGroupRelation> rolesCustomerGroupRelations = rolesCustomerGroupRelationMapper.selectAllByRoleId(roleId);
                //封装
                vo.setRoleId(roleId);
                vo.setGroupRelationList(rolesCustomerGroupRelations);
                vo.setNumber(roles.getNumber());
                vo.setPermissionList(permissions);
                vo.setRolesDepartmentRelationList(rolesDepartmentRelations);
                return  RespVO.ofSuccess(vo);
            case 4:
                //查看部门权限
                List<RolesDepartmentRelation> relationList = rolesDepartmentRelationMapper.selectAllByRoleIdAndType(roleId, 1);
                //录入次数
                Roles r = rolesMapper.selectByPrimaryKey(roleId);
                //功能权限
                List<Permission> permissionsList = permissionMapper.selectPermissionByRose(parentId, 1, roleId);
                //公客权限
                List<RolesCustomerGroupRelation> relations = rolesCustomerGroupRelationMapper.selectAllByRoleId(roleId);
                //封装
                vo.setRoleId(roleId);
                vo.setGroupRelationList(relations);
                vo.setNumber(r.getNumber());
                vo.setPermissionList(permissionsList);
                vo.setRolesDepartmentRelationList(relationList);
                return  RespVO.ofSuccess(vo);
        }
        return null;
    }

    /**
     * 当前角色所有权限
     * @param roleId
     * @return
     */
    public RespVO loadPermission(Integer roleId){
        List<Permission> permissions = permissionMapper.loadPermission(roleId);
        return RespVO.ofSuccess(permissions);
    }


    /**
     * 获取权限列表
     * @return
     */
    public RespVO selectAllByParentId(Integer pid,Integer type){
        List<Permission> permissions = permissionMapper.selectAllByParentId(pid, type);
        return RespVO.ofSuccess(permissions);
    }

    /**
     * 设置权限
     * @return
     */
    public RespVO update(List<Integer> ids,Integer status,Integer roleId){
        List<Integer> list=new ArrayList<>();
        for (Integer id :ids){
          list.addAll(permissionMapper.getIds(id));
        }
        //本身父级也要修改
        list.addAll(ids);
        rolesPermissionRelationMapper.updateStatusByPermissionIdInAndRoleId(status,list,roleId);
        return RespVO.ofSuccess();
    }

}


