package com.bibe.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.*;
import com.bibe.crm.entity.dto.ProgressDTO;
import com.bibe.crm.entity.po.CommentInfo;
import com.bibe.crm.entity.po.CustomerContact;
import com.bibe.crm.entity.po.User;
import com.bibe.crm.entity.vo.ProgressVO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.utils.ShiroUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.CustomerProgress;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerProgressService {

    @Resource
    private CustomerProgressMapper customerProgressMapper;

    @Resource
    private CustomerContactMapper customerContactMapper;

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentInfoMapper commentInfoMapper;


    public int delete(Integer id) {
        return customerProgressMapper.deleteByPrimaryKey(id);
    }


    public int add(CustomerProgress record) {
        return customerProgressMapper.insertSelective(record);
    }


    /**
     * 详情
     * @param id
     * @return
     */
    public RespVO show(Integer id) {

        Map<String,Object> map=new HashMap<>();
        //详情
        Map<String, Object> show = customerProgressMapper.show(id);
        if (show!=null){
            if (show.get("filesId")!=null){
                String filesId = (String) show.get("filesId");
                String[] ids = filesId.split(",");
                List<Map<String, Object>> fileByIds = filesMapper.findFileByIds(ids);
                map.put("files",fileByIds);
            }
            if (show.get("appointUserId")!=null){
                Integer userId = (Integer) show.get("appointUserId");
                User user = userMapper.selectByPrimaryKey(userId);
                show.put("appointUserName",user.getName());
            }
        }
        map.put("vo",show);
        return RespVO.ofSuccess(map);
    }


    public int update(CustomerProgress record) {
        return customerProgressMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 分页列表
     * @param dto
     * @param page
     * @return
     */
    public RespVO  pageList(ProgressDTO dto,Page page){
        List<Integer> userIds = dto.getUserIds();
        if (null != dto.getDeptId()) {
            userIds = userMapper.findIdByDeptId(dto.getDeptId());
        }
        IPage<ProgressVO> pageList = customerProgressMapper.pageList(dto, page, userIds);
        //评论列表
        pageList.getRecords().forEach(i->{
            List<Map<String, Object>> comment = commentInfoMapper.findAllByProgressId(i.getId());
            i.setCommentInfo(comment);
        });
        return RespVO.ofSuccess(pageList);
    }


    public RespVO  list(Integer customerId){
        List<ProgressVO> list = customerProgressMapper.list(customerId);
        return RespVO.ofSuccess(list);
    }


    /**
     * 联系人列表
     * @return
     */
    public RespVO contactList() {
        List<CustomerContact> list = customerContactMapper.list();
        return RespVO.ofSuccess(list);
    }


    /**
     * 联系人详情
     * @param id
     * @return
     */
    public RespVO contactShow(Integer id) {
        Map<String, Object> list = customerContactMapper.show(id);
        return RespVO.ofSuccess(list);
    }


    /**
     * 添加联系人
     * @param customerContact
     * @return
     */
    public RespVO contactAdd(CustomerContact customerContact) {
        CustomerContact flag = customerContactMapper.checkCustomerType(customerContact.getCustomerId());
        if (flag!=null){
            return RespVO.fail(ExceptionTypeEnum.INSTALL_CONTACT_ERROR);
        }
        customerContactMapper.insertSelective(customerContact);
        return RespVO.ofSuccess();
    }


    /**
     * 删除联系人
     * @param id
     * @return
     */
    public RespVO contactDel(Integer id) {
        customerContactMapper.deleteByPrimaryKey(id);
        return RespVO.ofSuccess();
    }

    /**
     * 修改联系人
     * @param customerContact
     * @return
     */
    public RespVO contactUpdate(CustomerContact customerContact) {
        CustomerContact flag = customerContactMapper.checkCustomerType(customerContact.getCustomerId());
        if (flag!=null){
            return RespVO.fail(ExceptionTypeEnum.INSTALL_CONTACT_ERROR);
        }
        customerContactMapper.updateByPrimaryKeySelective(customerContact);
        return RespVO.ofSuccess();
    }


    /**
     * 联系人分页列表
     * @param customerId
     * @param page
     * @return
     */
    public  RespVO contactPageList(Integer customerId,Page page){
        IPage<Map<String, Object>> mapIPage = customerContactMapper.pageList(customerId, page);
        return RespVO.ofSuccess(mapIPage);
    }


    /**
     * 添加评论
     * @param commentInfo
     * @return
     */
    public  RespVO addComment(CommentInfo commentInfo){
        commentInfo.setUserId(ShiroUtils.getUserInfo().getId());
        commentInfoMapper.insertSelective(commentInfo);
        return RespVO.ofSuccess();
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    public  RespVO deleteComment(Integer id){
        User userInfo = ShiroUtils.getUserInfo();
        //管理员直接删除
        if (userInfo.getRoleId().equals(1)){
            commentInfoMapper.deleteByPrimaryKey(id);
            return RespVO.ofSuccess();
        }
        CommentInfo commentInfo = commentInfoMapper.selectByPrimaryKey(id);
        if (commentInfo.getUserId().equals(userInfo.getId())){
            commentInfoMapper.deleteByPrimaryKey(id);
            return RespVO.ofSuccess();
        }else {
            return RespVO.fail(ExceptionTypeEnum.DELETE_COMMENT_ERROR);
        }

    }
}


