package com.bibe.crm.api;

import com.bibe.crm.entity.dto.PermissionDTO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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

}
