package com.bibe.crm.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.bibe.crm.entity.po.Permission;import java.util.Set;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    //获取全部权限接口地址
    Set<String> selectPath();

    /**
     * 获取权限列表
     * @param parentId
     * @return
     */
    List<Permission> selectAllByParentId(@Param("parentId")Integer parentId,@Param("type")Integer type);

    /**
     * 获取当前角色拥有的权限
     * @param parentId
     * @param type 0菜单 1功能
     * @param roleId
     * @return
     */
    List<Permission> selectPermissionByRose(@Param("parentId")Integer parentId,@Param("type")Integer type,@Param("roleId")Integer roleId);

    /**
     * 初始化角色权限
     * @param roleId
     * @return
     */
    List<Permission> loadPermission(@Param("roleId")Integer roleId);
}