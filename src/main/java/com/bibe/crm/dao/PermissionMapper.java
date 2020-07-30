package com.bibe.crm.dao;
import java.util.Collection;
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

    /**
     * 启用 禁止 子权限
     * @param updatedStatus
     * @param parentId
     * @return
     */
    int updateStatusByParentId(@Param("updatedStatus")Integer updatedStatus,@Param("parentId")Integer parentId);


    /**
     *  启用 禁止 父权限
     * @param updatedStatus
     * @param id
     * @return
     */
    int updateStatusById(@Param("updatedStatus")Integer updatedStatus,@Param("id")Integer id);


    List<Integer> getIds(@Param("parentId")Integer parentId);
}