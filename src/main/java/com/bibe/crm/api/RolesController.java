package com.bibe.crm.api;


import com.bibe.crm.entity.po.Department;
import com.bibe.crm.entity.po.Roles;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.RolesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/roles")
public class RolesController {


    @Resource
    private RolesService rolesService;


    /**
     * 列表
     * @return
     */
    @GetMapping("/list")
    public RespVO list(){
        return RespVO.ofSuccess(rolesService.list());
    }


    /**
     * 修改
     * @param roles
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody Roles roles){
        return rolesService.update(roles);
    }

    /**
     * 添加
     * @param
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody Roles roles){
        return rolesService.add(roles);
    }


    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/show")
    public RespVO<Department> show(Integer id){
        return  RespVO.ofSuccess(rolesService.show(id));
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer id){
        return rolesService.delete(id);
    }
}
