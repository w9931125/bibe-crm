package com.bibe.crm.utils;

import com.bibe.crm.dao.CustomerGroupMapper;
import com.bibe.crm.entity.po.CustomerGroup;
import com.bibe.crm.entity.vo.CustomerGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自动转交公客
 */
@Component
@Slf4j
public class Task {

    @Resource
    private CustomerGroupMapper customerGroupMapper;

    //凌晨执行
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduled(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh点mm分ss秒");
        // 将date日期解析为字符串
        String date = simpleDateFormat.format(new Date());
        log.info("自动转交公客开始执行>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("当前时间：" + date);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<自动转交公客结束");

        List<CustomerGroup> list = customerGroupMapper.groupList();
        list.forEach(i->{
            Integer endDay = i.getEndDay();

        });
    }

    //月初
    @Scheduled(cron = "0 10 0 1 * ?")
    public void month(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh点mm分ss秒");
        // 将date日期解析为字符串
        String date = simpleDateFormat.format(new Date());
        log.info("当前时间：" + date);
    }

}
