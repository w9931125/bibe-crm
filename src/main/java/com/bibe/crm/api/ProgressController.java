package com.bibe.crm.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.common.base.BasePage;
import com.bibe.crm.entity.dto.FindCustomerDTO;
import com.bibe.crm.entity.dto.ProgressDTO;
import com.bibe.crm.entity.po.CommentInfo;
import com.bibe.crm.entity.po.CustomerContact;
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
     * 联系人分页列表
     * @param customerId
     * @return
     */
    @GetMapping("/contactPageList")
    public RespVO contactPageList(Integer customerId,BasePage basePage){
        Page page = basePage.getPage();
        return RespVO.ofSuccess(customerProgressService.contactPageList(customerId,page));
    }


    /**
     * 联系人修改
     * @param customerContact
     * @return
     */
    @PutMapping("/contactUpdate")
    public RespVO contactUpdate(@RequestBody CustomerContact customerContact){
        return RespVO.ofSuccess(customerProgressService.contactUpdate(customerContact));
    }


    /**
     * 联系人添加
     * @param customerContact
     * @return
     */
    @PostMapping("/contactAdd")
    public RespVO contactAdd(@RequestBody CustomerContact customerContact){
        return RespVO.ofSuccess(customerProgressService.contactAdd(customerContact));
    }


    /**
     * 联系人删除
     * @param id
     * @return
     */
    @DeleteMapping("/contactDel")
    public RespVO contactDel(Integer id){
        return RespVO.ofSuccess(customerProgressService.contactDel(id));
    }

    /**
     * 修改跟进
     * @param customerProgress
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody CustomerProgress customerProgress){
        customerProgressService.update(customerProgress);
        return RespVO.ofSuccess();
    }

    /**
     * 添加跟进
     * @param
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody CustomerProgress customerProgress){
        customerProgressService.add(customerProgress);
        return RespVO.ofSuccess();
    }


    /**
     * 详情/编辑跟进
     * @param id
     * @return
     */
    @GetMapping("/show")
    public RespVO show(Integer id){
        return  RespVO.ofSuccess(customerProgressService.show(id));
    }


    /**
     * 删除跟进
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer id){
        customerProgressService.delete(id);
        return RespVO.ofSuccess();
    }


    /**
     * 跟进分页列表
     * @param dto
     * @return
     */
    @PostMapping("/pageList")
    public RespVO pageList(@RequestBody ProgressDTO dto){
        Page page = dto.getPage();
        return customerProgressService.pageList(dto,page);
    }


    /**
     * 新增评论
     * @param commentInfo
     * @return
     */
    @PostMapping("/addComment")
    public RespVO addComment(@RequestBody CommentInfo commentInfo){
        customerProgressService.addComment(commentInfo);
        return RespVO.ofSuccess();
    }


    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/deleteComment")
    public RespVO deleteComment(Integer id){
        customerProgressService.deleteComment(id);
        return RespVO.ofSuccess();
    }
}
