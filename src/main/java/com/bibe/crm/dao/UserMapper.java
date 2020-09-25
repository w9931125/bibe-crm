package com.bibe.crm.dao;
import java.util.Date;
import java.util.Collection;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.entity.dto.UserPageDTO;
import com.bibe.crm.entity.po.User;
import com.bibe.crm.entity.vo.DeptNameVO;
import com.bibe.crm.entity.vo.IdNameVO;
import com.bibe.crm.entity.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findAllByPhone(@Param("phone") String phone);

    /**
     * 验证重名问题
     * @param name
     * @return
     */
   int selectCountByName(@Param("name")String name);



    /**
     * 详情
     * @param id
     * @return
     */
    UserVO show(@Param("id")Integer id);

    /**
     * 列表
     *
     * @return
     */
   IPage<UserVO> pageList(@Param("dto")UserPageDTO userPageDTO, Page page);

    /**
     * 验证部门下是否存在员工
     * @param deptId
     * @return
     */
   int selectCountByDeptId(@Param("deptId")Integer deptId);


    /**
     * 验证职务
     * @param roleId
     * @return
     */
   Integer selectCountByRoleId(@Param("roleId")Integer roleId);


    /**
     * 批量删除
     * @param ids
     * @return
     */
   int deletebyIdIn(@Param("ids")Integer[] ids);

    /**
     * 验证密码
     * @param password
     * @return
     */
    int selectCountByPassword(@Param("password")String password,@Param("id") Integer id);

    /**
     * 设置录入客户
     * @param updatedNumber
     * @param roleId
     * @return
     */
    int updateNumberByRoleId(@Param("updatedNumber")Integer updatedNumber,@Param("roleId")Integer roleId);


    /**
     * 选择团队成员
     * @param name
     * @param deptIds
     * @return
     */
//     List<DeptNameVO> selectNameDeptNameList(@Param("name") String name, @Param("ids")List<Integer> deptIds);

     List<DeptNameVO> selectNameDeptName(@Param("name") String name, @Param("deptIds") List<Integer> deptIds);

    /**
     * 指定人员-按人员浏览
     * @return
     */
     List<Map<String,Object>> findUserByDeptId(@Param("ids")List<Integer> deptIds);

    /**
     * 基本信息
     * @return
     */
     List<Map<String,Object>> findBaseInfo(@Param("deptId")Integer deptId);
     List<Map<String,Object>> findBaseInfoByUserId(@Param("userId")Integer userId);

    /**
     * 根据部门
     * @param deptId
     * @return
     */
     List<IdNameVO> findAllByDeptId(@Param("deptId")Integer deptId);


    /**
     * 减少录入次数
     * @param id
     * @return
     */
    int updateNumberById(@Param("id")Integer id);


    /**
     * 当前部门所有员工id
     * @param deptId
     * @return
     */
    List<Integer> findIdByDeptId(@Param("deptId")Integer deptId);

}