package com.bibe.crm.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.entity.dto.CustomerDTO;
import com.bibe.crm.entity.dto.FindCustomerDTO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {


    @Resource
    private CustomerService customerService;
    /**
     * 修改
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody CustomerDTO dto,List<Integer> ids){
        return customerService.update(dto,ids);
    }

    /**
     * 添加
     * @param customerDTO
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody CustomerDTO customerDTO){
        return customerService.add(customerDTO);
    }


    /**
     * 编辑
     * @param id
     * @return
     */
    @GetMapping("/edit")
    public RespVO edit(Integer id){
        return customerService.edit(id);
    }


    /**
     * 分页列表
     * @param dto
     * @return
     */
    @PostMapping("/pageList")
    public RespVO pageList(@RequestBody FindCustomerDTO dto){
        Page page = dto.getPage();
        return customerService.pageList(dto,page);
    }




    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer[] ids){
         customerService.delete(ids);
         return RespVO.ofSuccess();
    }

//
//    /**
//     * 树部门
//     * @return
//     */
//    @GetMapping("/tree")
//    public RespVO<List<TreeData>> tree(){
//        return RespVO.ofSuccess(customerService.tree());
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
//        return customerService.deptNameList(name,deptId);
//    }


}
