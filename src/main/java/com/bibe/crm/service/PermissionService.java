package com.bibe.crm.service;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.*;
import com.bibe.crm.entity.dto.PermissionDTO;
import com.bibe.crm.entity.dto.PermissionUpdateDTO;
import com.bibe.crm.entity.po.*;
import com.bibe.crm.entity.vo.*;
import com.bibe.crm.utils.DepartmentUtil;
import com.bibe.crm.utils.ShiroUtils;
import com.bibe.crm.utils.TreeUtil;
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

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private DepartmentUtil departmentUtil;


    /**
     *
     * @param flag 0客户资料 1联系跟进
     * @return
     */
    public RespVO findInput(Integer flag){
        User user = ShiroUtils.getUserInfo();
        //指定人员
        Map<String,Object> userMap=new HashMap<>();
        //指定部门
        Map<String,Object> deptMap=new HashMap<>();
        //最后封装map
        Map<String,Object> map=new HashMap<>();
        //管理员直接返回全部
        if (user.getRoleId().equals(1)){
            //按人员
            List<Map<String, Object>> baseInfo = userMapper.findBaseInfo(null);
            userMap.put("users",baseInfo);
            //按部门
            List<DeptInputVO> baseDept = departmentMapper.findBaseDept();
            for (DeptInputVO deptInputVO : baseDept) {
                List<IdNameVO> allByDeptId = userMapper.findAllByDeptId(deptInputVO.getId());
                deptInputVO.setUsers(allByDeptId);
            }
            userMap.put("users",baseInfo);
            userMap.put("depts",baseDept);
            //指定部门
            deptMap.put("dept",TreeUtil.getTreeList(departmentMapper.tree(),0));
            //最后封装
            map.put("appointUser",userMap);
            map.put("appointDept",deptMap);
            return RespVO.ofSuccess(map);
        }
        //客户资料查询权限
        List<RolesDepartmentRelation> rolesDepartmentRelationList = rolesDepartmentRelationMapper.selectAllByRoleIdAndType(user.getRoleId(), flag);
        if (rolesDepartmentRelationList.size()>1){
            //按人员浏览
            List<Integer> deptIds=new ArrayList<>();
            rolesDepartmentRelationList.forEach(i->deptIds.add(i.getDeptId()));
            List<Map<String, Object>> userDeptVO = userMapper.findUserByDeptId(deptIds);
            //按部门浏览
            List<DeptInputVO> deptInputVOS = departmentMapper.selectAllBYIdIn(deptIds);
            for (DeptInputVO deptInputVO : deptInputVOS) {
                List<IdNameVO> allByDeptId = userMapper.findAllByDeptId(deptInputVO.getId());
                deptInputVO.setUsers(allByDeptId);
            }

            userMap.put("users",userDeptVO);
            userMap.put("depts",deptInputVOS);
            //指定部门
            deptMap.put("dept",departmentMapper.findAllByIdIn(deptIds));
            //最后封装
            map.put("appointUser",userMap);
            map.put("appointDept",deptMap);
            return RespVO.ofSuccess(map);
        }else {
            RolesDepartmentRelation rolesDepartmentRelation = rolesDepartmentRelationList.get(0);
            Integer deptId = rolesDepartmentRelation.getDeptId();
            /**
             * 职位指定查看部门权限
             * 禁止查看为0
             * 查看所有为-1
             * 只看自己为-2
             * 同步客户-3
             * -4与自己同一个部门
             * -5与自己同一个部门及下级部门
             * 其他按部门id记录
             */
            if (deptId.equals(0)){
                return RespVO.fail(ExceptionTypeEnum.SELECT_DEPT_BAN);
            }else if (deptId.equals(-1)){
                //按人员
                List<Map<String, Object>> baseInfo = userMapper.findBaseInfo(null);
                userMap.put("users",baseInfo);
                //按部门
                List<DeptInputVO> baseDept = departmentMapper.findBaseDept();
                for (DeptInputVO deptInputVO : baseDept) {
                    List<IdNameVO> allByDeptId = userMapper.findAllByDeptId(deptInputVO.getId());
                    deptInputVO.setUsers(allByDeptId);
                }
                userMap.put("users",baseInfo);
                userMap.put("depts",baseDept);
                //指定部门
                deptMap.put("dept",TreeUtil.getTreeList(departmentMapper.tree(),0));
                //最后封装
                map.put("appointUser",userMap);
                map.put("appointDept",deptMap);
                return RespVO.ofSuccess(map);
            }else if (deptId.equals(-2)){
                return RespVO.fail(ExceptionTypeEnum.SELECT_DEPT_BAN);
            }else if (deptId.equals(-4)){
                //按人员
                List<Map<String, Object>> allByDeptId = userMapper.findBaseInfo(user.getDeptId());
                //按部门
                DeptInputVO baseDeptById = departmentMapper.findBaseDeptById(user.getDeptId());
                baseDeptById.setUsers(userMapper.findAllByDeptId(baseDeptById.getId()));
                userMap.put("users",allByDeptId);
                userMap.put("depts",baseDeptById);
                //指定部门
                deptMap.put("dept",departmentMapper.treeById(user.getDeptId()));
                //最后封装
                map.put("appointUser",userMap);
                map.put("appointDept",deptMap);
                return RespVO.ofSuccess(map);
            }else if (deptId.equals(-5)){
                List<Integer> childDeptIds = departmentUtil.getChildDeptId(user.getDeptId());
                //按人员
                List<Map<String, Object>> userByDeptId = userMapper.findUserByDeptId(childDeptIds);
                //按部门
                List<DeptInputVO> deptInputVOS = departmentMapper.selectAllBYIdIn(childDeptIds);
                for (DeptInputVO deptInputVO : deptInputVOS) {
                    List<IdNameVO> allByDeptId = userMapper.findAllByDeptId(deptInputVO.getId());
                    deptInputVO.setUsers(allByDeptId);
                }
                userMap.put("users",userByDeptId);
                userMap.put("depts",deptInputVOS);
                //指定部门
                deptMap.put("dept",TreeUtil.getTreeList(departmentMapper.findAllByIdIn(childDeptIds),user.getDeptId()));
                //最后封装
                map.put("appointUser",userMap);
                map.put("appointDept",deptMap);
                return RespVO.ofSuccess(map);
            }
            return null;
        }
    }




    /**
     * 当前角色拥有的权限部门列表
     * @return
     */
    public RespVO permissionDeptList(){
        User userInfo = ShiroUtils.getUserInfo();
        //管理员账号直接返回全部权限
        if (userInfo.getRoleId().equals(1)){
            List<TreeData> tree = departmentMapper.tree();
            return RespVO.ofSuccess(TreeUtil.getTreeList(tree,0));
        }
        //客户资料查询权限
        List<RolesDepartmentRelation> rolesDepartmentRelationList = rolesDepartmentRelationMapper.selectAllByRoleIdAndType(userInfo.getRoleId(), 0);
        if (rolesDepartmentRelationList.size()>1){
            List<Integer> deptIds=new ArrayList<>();
            rolesDepartmentRelationList.forEach(i->deptIds.add(i.getDeptId()));
            List<TreeData> treeDataList = departmentMapper.findAllByIdIn(deptIds);
            return RespVO.ofSuccess(treeDataList);
        }else {
            RolesDepartmentRelation rolesDepartmentRelation = rolesDepartmentRelationList.get(0);
            Integer deptId = rolesDepartmentRelation.getDeptId();
            return checkFindDeptPermission(deptId,userInfo);
        }
    }


    /**
     * 选择查询权限
     * @param dept
     * @param user
     * @return
     */
    private RespVO checkFindDeptPermission(Integer dept,User user){
        /**
         * 职位指定查看部门权限
         * 禁止查看为0
         * 查看所有为-1
         * 只看自己为-2
         * 同步客户-3
         * -4与自己同一个部门
         * -5与自己同一个部门及下级部门
         * 其他按部门id记录
         */

        if (dept.equals(0)){
            return RespVO.fail(ExceptionTypeEnum.SELECT_DEPT_BAN);
        }else if (dept.equals(-1)){
            List<TreeData> tree = departmentMapper.tree();
            return RespVO.ofSuccess(TreeUtil.getTreeList(tree,0));
        }else if (dept.equals(-2)){
            return RespVO.fail(ExceptionTypeEnum.SELECT_DEPT_BAN);
        }else if (dept.equals(-4)){
            return RespVO.ofSuccess(departmentMapper.treeById(user.getDeptId()));
        }else if (dept.equals(-5)){
            List<Integer> childDeptIds = departmentUtil.getChildDeptId(user.getDeptId());
            return RespVO.ofSuccess(TreeUtil.getTreeList(departmentMapper.findAllByIdIn(childDeptIds),user.getDeptId()));
        }
        return null;
    }


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
                    //如果为同步客户资料
                    if (dto.getRolesDepartId().equals(-3)){
                        List<RolesDepartmentRelation> rolesDepartmentRelationList = rolesDepartmentRelationMapper.selectAllByRoleIdAndType(dto.getRoleId(), 0);
                        if (rolesDepartmentRelationList.size()==0||rolesDepartmentRelationList==null){
                            return RespVO.fail(ExceptionTypeEnum.PERMISSION_SYSN_ERROR);
                        }
                        rolesDepartmentRelationMapper.deleteByRoleId(dto.getRoleId(),1);
                        rolesDepartmentRelationList.forEach(o->o.setType(1));
                        rolesDepartmentRelationMapper.insertList(rolesDepartmentRelationList);
                    }
                    rolesDepartmentRelationMapper.deleteByRoleId(dto.getRoleId(),1);
                    //   部门id -1为全部客户 0为禁止 -2只看自己负责 -3同步客户资料 -4与自己同一个部门 -5与自己同一个部门及下级部门
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
        if (roleId.equals(1)){
            List<Permission> permissions = permissionMapper.loadAdminPermission();
            return RespVO.ofSuccess(permissions);
        }
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
    public RespVO update(List<PermissionUpdateDTO> list){

        List<PermissionUpdateDTO> obj=new ArrayList<>();
        Iterator<PermissionUpdateDTO> iterator = list.iterator();
        while (iterator.hasNext()){
            PermissionUpdateDTO next = iterator.next();
            List<Integer> ids = permissionMapper.getIds(next.getId());
            for (Integer id : ids) {
                PermissionUpdateDTO  update= new PermissionUpdateDTO();
                update.setId(id);
                update.setStatus(next.getStatus());
                update.setRoleId(next.getRoleId());
                obj.add(update);
            }
        }
        obj.addAll(list);
        obj.forEach(i->rolesPermissionRelationMapper.updateStatusByPermissionIdAndRoleId(i.getStatus(),i.getId(),i.getRoleId()));
        return RespVO.ofSuccess();
    }

}