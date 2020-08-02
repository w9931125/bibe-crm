package com.bibe.crm.dao;
import java.util.Collection;
import com.bibe.crm.entity.vo.TreeData;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.bibe.crm.entity.po.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    /**
     * 验证是否存在子集部门
     * @param parentId
     * @return
     */
    int selectCountByParentId(@Param("parentId")Integer parentId);

    /**
     * 部门列表
     * @param parentId
     * @return
     */
    List<Department> list(@Param("parentId")Integer parentId);

    /**
     * 树部门
     * @return
     */
    List<TreeData> tree();


    TreeData treeById(Integer id);


    List<TreeData> findAllByIdIn(@Param("idCollection")Collection<Integer> idCollection);

    List<Integer> findIdByParentId(@Param("parentId")Integer parentId);

    List<Integer> findIdByParentIdIn(@Param("parentIdCollection")Collection<Integer> parentIdCollection);

}