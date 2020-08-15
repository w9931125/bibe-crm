package com.bibe.crm.service;

import com.bibe.crm.dao.*;
import com.bibe.crm.entity.po.CustomerContact;
import com.bibe.crm.entity.po.User;
import com.bibe.crm.entity.vo.RespVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.CustomerProgress;

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


    public int updateByPrimaryKey(CustomerProgress record) {
        return customerProgressMapper.updateByPrimaryKey(record);
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

}


