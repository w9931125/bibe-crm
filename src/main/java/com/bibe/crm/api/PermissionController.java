package com.bibe.crm.api;

import com.bibe.crm.entity.dto.PermissionDTO;
import com.bibe.crm.entity.dto.PermissionUpdateDTO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/permission")
public class PermissionController {


    @Resource
    private PermissionService permissionService;


    /**
     * 添加
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody PermissionDTO dto) {
       return  permissionService.add(dto);
    }

    /**
     *  详情
     *
     * @return
     */
    @GetMapping("/show")
    public RespVO show(Integer roleId,Integer parentId) {
        return permissionService.show(roleId, parentId);
    }

    /**
     * 初始化角色权限
     * @param roleId
     * @return
     */
    @GetMapping("/loadPermission")
    public RespVO loadPermission(Integer roleId) {
        return permissionService.loadPermission(roleId);
    }

    /**
     * 获取列表
     * @param parentId
     * @param type
     * @return
     */
    @GetMapping("/selectAllByParentId")
    public RespVO selectAllByParentId(Integer parentId,Integer type) {
        return permissionService.selectAllByParentId(parentId, type);
    }


    /**
     * 当前角色查询权限下拉框
     * flag 0客户资料 1联系跟进
     * @return
     */
    @GetMapping("/findInput")
    public RespVO findInput(Integer flag) {
        return permissionService.findInput(flag);
    }

    /**
     * 当前角色公客分组
     * @return
     */
    @GetMapping("/findCustomerGroup")
    public RespVO findCustomerGroup() {
        return permissionService.permissionCustomerGroupList();
    }

    /**
     * 当前角色拥有的权限部门列表
     * @param move 1转交查看
     * @return
     */
    @GetMapping("/findPermissionDept")
    public RespVO findPermissionDept(Integer move) {
        return permissionService.permissionDeptList(move);
    }

    /**
     * 修改状态
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody PermissionDTO dto) {
        return permissionService.update(dto.getPermissionUpdateDTO());
    }
}
