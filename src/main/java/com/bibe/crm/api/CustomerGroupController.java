package com.bibe.crm.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.entity.dto.CustomerGroupDTO;
import com.bibe.crm.entity.dto.FindCustomerGroupDTO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.CustomerGroupService;
import com.bibe.crm.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/customerGroup")
public class CustomerGroupController {


    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerGroupService customerGroupService;

    
    /**
     * 修改
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody CustomerGroupDTO dto){
        return customerGroupService.update(dto);
    }

    /**
     * 添加
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody CustomerGroupDTO dto){
        return customerGroupService.add(dto);
    }


    /**
     * 编辑
     * @param id
     * @return
     */
    @GetMapping("/show")
    public RespVO show(Integer id){
        return customerGroupService.show(id);
    }


    /**
     * 分页列表
     * @param dto
     * @return
     */
    @PostMapping("/pageList")
    public RespVO pageList(@RequestBody FindCustomerGroupDTO dto){
        Page page = dto.getPage();
        return customerService.customerGroupPageList(dto,page);
    }


    /**
     * 设置公客分组列表
     * @return
     */
    @GetMapping("/list")
    public RespVO list(){
        return customerGroupService.list();
    }



    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer id){
         customerGroupService.delete(id);
         return RespVO.ofSuccess();
    }

//
//    /**
//     * 树部门
//     * @return
//     */
//    @GetMapping("/tree")
//    public RespVO<List<TreeData>> tree(){
//        return RespVO.ofSuccess(customerGroupService.tree());
//    }
//
//
//    /**
//     * 选择团队成员
//     * @param name
//     * @param deptId
//     * @return
//     */
//    @GetMapping("/selectUserDept")
//    public RespVO selectUserDept(String name,Integer deptId){
//        return customerGroupService.deptNameList(name,deptId);
//    }


}
