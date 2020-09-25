package com.bibe.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.CustomerMapper;
import com.bibe.crm.entity.dto.UserDTO;
import com.bibe.crm.entity.dto.UserPageDTO;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.entity.vo.UserVO;
import com.bibe.crm.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.User;
import com.bibe.crm.dao.UserMapper;

import java.util.Date;

@Service
@Slf4j
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CustomerMapper customerMapper;


    public RespVO deleteByPrimaryKey(Integer[] ids) {
        for (int i = 0; i < ids.length; i++) {
            Integer q = customerMapper.selectCountByUserId(ids[i]);
            //检查是否有客户
            if (q>0){
                User user = userMapper.selectByPrimaryKey(ids[i]);
                return RespVO.fail(ExceptionTypeEnum.USER_CUSTOMER_ERROR,user.getName());
            }
        }
        userMapper.deletebyIdIn(ids);
        return RespVO.ofSuccess();
    }


    public RespVO insert(UserDTO record) {
        User allByPhone = userMapper.findAllByPhone(record.getPhone());
        if (allByPhone!=null){
            return RespVO.fail(ExceptionTypeEnum.USER_BY_PHONE);
        }
        if (!record.getPassword().equals(record.getRepeatPassword())){
            return RespVO.fail(ExceptionTypeEnum.PASSWORD_ERROR);
        }

        int i = userMapper.selectCountByName(record.getName());
        if (i>0){
            return RespVO.fail(ExceptionTypeEnum.USER_COUNT_ERROR);
        }
        User user=new User();
        BeanUtils.copyProperties(record,user);
        user.setPassword(getPassword(user.getPassword()));
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
        return RespVO.ofSuccess();
    }


    public RespVO<UserVO> show(Integer id) {
        UserVO show = userMapper.show(id);
        return RespVO.ofSuccess(show);
    }


    public void updateBy(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }


    public RespVO update(UserDTO record) {
        User allByPhone = userMapper.findAllByPhone(record.getPhone());
        if (allByPhone!=null&&!record.getId().equals(allByPhone.getId())){
            return RespVO.fail(ExceptionTypeEnum.USER_BY_PHONE);
        }
        User user=new User();
        BeanUtils.copyProperties(record,user);
        if (user.getPassword()!=null){
            user.setPassword(getPassword(user.getPassword()));
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return RespVO.ofSuccess();
    }


    public IPage pageList(UserPageDTO userPageDTO, Page page){
        IPage<UserVO> userVOIPage = userMapper.pageList(userPageDTO, page);
        return userVOIPage;
    }

    public RespVO checkPassword(String password){

        String password1 = getPassword(password);
        int i = userMapper.selectCountByPassword(password1, ShiroUtils.getUserInfo().getId());
        if (i>0){
            return RespVO.ofSuccess();
        }else {
            return RespVO.fail(ExceptionTypeEnum.CHECK_PASSWORD_ERROR);
        }
    }

    private String getPassword(String password){
        Md5Hash md5Hash = new Md5Hash(password,null,2);
        String s = md5Hash.toString();
        return s;
    }
}




