package com.bibe.crm.api;


import com.bibe.crm.entity.po.Department;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.entity.vo.TreeData;
import com.bibe.crm.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {


    @Resource
    private DepartmentService departmentService;


    /**
     * 列表
     * @param parentId
     * @return
     */
    @GetMapping("/list")
    public RespVO list(Integer parentId){
        return RespVO.ofSuccess(departmentService.list(parentId));
    }


    /**
     * 修改
     * @param department
     * @return
     */
    @PutMapping("/update")
    public RespVO update(@RequestBody Department department){
        return departmentService.update(department);
    }

    /**
     * 添加
     * @param department
     * @return
     */
    @PostMapping("/add")
    public RespVO add(@RequestBody Department department){
        return departmentService.insert(department);
    }


    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/show")
    public RespVO<Department> show(Integer id){
        return departmentService.show(id);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespVO delete(Integer id){
        return departmentService.deleteByPrimaryKey(id);
    }


    /**
     * 树部门
     * @return
     */
    @GetMapping("/tree")
    public RespVO<List<TreeData>> tree(){
        return RespVO.ofSuccess(departmentService.tree());
    }
}
