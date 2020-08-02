package com.bibe.crm.utils;

import com.bibe.crm.dao.DepartmentMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 处理部门工具
 */
 @Component
public class DepartmentUtil {


    @Resource
    private DepartmentMapper departmentMapper;

    private List<Integer> idList=new ArrayList<>();


    public List<Integer> getChildDeptId(Integer id) {
        List<Integer> ids=getIds(id);
        ids.add(id);
        List<Integer> addressList=departmentMapper.findIdByParentIdIn(ids);
        //***用完后清空该集合，不然再次查询时，之前的查询的部门也在里面
        idList.clear();
        return addressList;
    }

    private List<Integer> getIds(Integer id){
        List<Integer> list=departmentMapper.findIdByParentId(id);
        for (int i=0;i<list.size();i++){
            idList.add(list.get(i));
            getIds(list.get(i));
        }
        return idList;
    }
}
