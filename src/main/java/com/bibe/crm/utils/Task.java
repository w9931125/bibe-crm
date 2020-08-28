package com.bibe.crm.utils;

import com.bibe.crm.dao.CustomerGroupMapper;
import com.bibe.crm.dao.CustomerMapper;
import com.bibe.crm.entity.po.Customer;
import com.bibe.crm.entity.po.CustomerGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自动转交公客
 */
@Component
@Slf4j
public class Task {

    @Resource
    private CustomerGroupMapper customerGroupMapper;
    @Resource
    private CustomerMapper customerMapper;

    //凌晨执行
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduled(){
        log.info("自动转交公客开始执行>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Integer  y=0;
        List<CustomerGroup> list = customerGroupMapper.groupList();
        for (CustomerGroup i : list) {
            //公客转交天数
            Integer endDay = i.getEndDay();
            String dayString = DateUtils.getAssignBeforeDayString(new Date(), -endDay);
            Customer vo = customerMapper.findAllByGroupId(i.getId(),dayString);
            if (null==vo){
                log.info(dayString+"没有自动转交客户");
            }else {
                customerMapper.updateGroupIdById(0,vo.getId());
                log.info(vo.getName()+"已被转交");
                y=y+1;
            }
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<自动转交公客结束,本次转交客户数量"+y);
    }
}
