package com.bibe.crm.api;


import com.bibe.crm.entity.po.CustomerProgress;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.CustomerProgressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/customerProgress")
public class ProgressController {


    @Resource
    private CustomerProgressService customerProgressService;


    /**
     * 联系人列表
     * @return
     */
    @GetMapping("/contactList")
    public RespVO list(){
        return RespVO.ofSuccess(customerProgressService.contactList());
    }

    /**
     * 联系人详情
     * @param id
     * @return
     */
    @GetMapping("/contactShow")
    public RespVO contactShow(Integer id){
        return RespVO.ofSuccess(customerProgressService.contactShow(id));
    }

    /**
     * 修改
     * @param customerProgress
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody CustomerProgress customerProgress){
        customerProgressService.update(customerProgress);
        return RespVO.ofSuccess();
    }

    /**
     * 添加
     * @param
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody CustomerProgress customerProgress){
        customerProgressService.add(customerProgress);
        return RespVO.ofSuccess();
    }


    /**
     * 详情/编辑
     * @param id
     * @return
     */
    @GetMapping("/show")
    public RespVO show(Integer id){
        return  RespVO.ofSuccess(customerProgressService.show(id));
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer id){
        customerProgressService.delete(id);
        return RespVO.ofSuccess();
    }
}
