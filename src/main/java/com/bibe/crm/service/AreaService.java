package com.bibe.crm.service;

import com.bibe.crm.entity.vo.RespVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bibe.crm.entity.po.Area;
import com.bibe.crm.dao.AreaMapper;
@Service
public class AreaService{

    @Resource
    private AreaMapper areaMapper;

    
    public RespVO list(Integer pid) {
        return RespVO.ofSuccess(areaMapper.list(pid));
    }

}
