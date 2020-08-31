package com.bibe.crm.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.common.base.BasePage;
import com.bibe.crm.entity.dto.FindCustomerDTO;
import com.bibe.crm.entity.dto.ProgressDTO;
import com.bibe.crm.entity.po.CommentInfo;
import com.bibe.crm.entity.po.CustomerContact;
import com.bibe.crm.entity.po.CustomerProgress;
import com.bibe.crm.entity.vo.ProgressVO;
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
    public RespVO list(Integer customerId){
        return customerProgressService.contactList(customerId);
    }

    /**
     * 联系人详情
     * @param id
     * @return
     */
    @GetMapping("/contactShow")
    public RespVO contactShow(Integer id){
        return customerProgressService.contactShow(id);
    }


    /**
     * 联系人分页列表
     * @param customerId
     * @return
     */
    @GetMapping("/contactPageList")
    public RespVO contactPageList(Integer customerId,BasePage basePage){
        Page page = basePage.getPage();
        return  customerProgressService.contactPageList(customerId,page);
    }


    /**
     * 联系人修改
     * @param customerContact
     * @return
     */
    @PutMapping("/contactUpdate")
    public RespVO contactUpdate(@RequestBody CustomerContact customerContact){
        return customerProgressService.contactUpdate(customerContact);
    }


    /**
     * 联系人添加
     * @param customerContact
     * @return
     */
    @PostMapping("/contactAdd")
    public RespVO contactAdd(@RequestBody CustomerContact customerContact){
        return customerProgressService.contactAdd(customerContact);
    }


    /**
     * 联系人删除
     * @param id
     * @return
     */
    @DeleteMapping("/contactDel")
    public RespVO contactDel(Integer id){
        return customerProgressService.contactDel(id);
    }

    /**
     * 修改跟进自己
     * @param customerProgress
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody CustomerProgress customerProgress){
        customerProgressService.update(customerProgress);
        return RespVO.ofSuccess();
    }

    /**
     * 修改别人
     * @param customerProgress
     * @return
     */
    @PutMapping("/updateHe")
    public RespVO updateHe(@RequestBody CustomerProgress customerProgress){
        customerProgressService.update(customerProgress);
        return RespVO.ofSuccess();
    }


    /**
     * 修改公客跟进
     * @param customerProgress
     * @return
     */
    @PutMapping("/updatePublic")
    public RespVO updatePublic(@RequestBody CustomerProgress customerProgress){
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
        return  customerProgressService.show(id);
    }


    /**
     * 删除跟进自己
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer id){
        customerProgressService.delete(id);
        return RespVO.ofSuccess();
    }


    /**
     * 删除跟进他人
     * @param id
     * @return
     */
    @DeleteMapping("/deleteHe")
    public RespVO deleteHe(Integer id){
        customerProgressService.delete(id);
        return RespVO.ofSuccess();
    }

    /**
     * 删除公客跟进
     * @param id
     * @return
     */
    @DeleteMapping("/deletePublic")
    public RespVO deletePublic(Integer id){
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
        IPage<ProgressVO> progressVOIPage = customerProgressService.pageList(dto, page);
        return  RespVO.ofSuccess(progressVOIPage);
    }


    /**
     * 新增评论
     * @param commentInfo
     * @return
     */
    @PostMapping("/addComment")
    public RespVO addComment(@RequestBody CommentInfo commentInfo){
        return customerProgressService.addComment(commentInfo);
    }


    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/deleteComment")
    public RespVO deleteComment(Integer id){
        return customerProgressService.deleteComment(id);
    }
}
