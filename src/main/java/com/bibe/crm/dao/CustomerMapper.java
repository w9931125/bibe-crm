package com.bibe.crm.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bibe.crm.entity.dto.CountDTO;
import com.bibe.crm.entity.dto.FindCustomerDTO;import com.bibe.crm.entity.dto.FindCustomerGroupDTO;import com.bibe.crm.entity.po.Customer;
import com.bibe.crm.entity.vo.CountDayVO;
import com.bibe.crm.entity.vo.CountSortVO;
import com.bibe.crm.entity.vo.CustomerVO;
import com.bibe.crm.entity.vo.PublicCustomerVO;
import org.apache.ibatis.annotations.Param;import java.util.Collection;import java.util.List;import java.util.Map;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    /**
     * 判断员工下是否有客户
     */
    Integer selectCountByUserId(Integer userId);

    /**
     * 批量修改
     *
     * @param updated
     * @param idCollection
     * @return
     */
    int updateByIdIn(@Param("updated") Customer updated, @Param("idCollection") Collection<Integer> idCollection);

    /**
     * 逻辑批量删除
     *
     * @param ids
     * @return
     */
    int updateStatusByIdIn(@Param("ids") Integer[] ids);

    /**
     * 客户分页列表
     *
     * @param dto
     * @param page
     * @param userIds
     * @return
     */
    IPage<CustomerVO> pageList(FindCustomerDTO dto, Page page, @Param("userIds") List<Integer> userIds);

    /**
     * 我的客戶
     * @param dto
     * @param page
     * @return
     */
    IPage<CustomerVO> myPageList(FindCustomerDTO dto, Page page,@Param("userId") Integer userId);

    /**
     * 公客分组分页列表
     *
     * @param dto
     * @param page
     * @return
     */
    IPage<PublicCustomerVO> customerGroupPageList(FindCustomerGroupDTO dto, Page page);

    /**
     * 领取/转交/
     *
     * @param updatedUserId
     * @param updatedGroupId
     * @param idCollection
     * @return
     */
    int updateUserIdAndGroupIdByIdIn(@Param("updatedUserId") Integer updatedUserId, @Param("updatedGroupId") Integer updatedGroupId, @Param("idCollection") Collection<Integer> idCollection);

    /**
     * 模糊查询客户
     *
     * @param name
     * @return
     */
    List<Map<String, Object>> findLikeName(@Param("name") String name);


    /**
     * 按时间统计
     * @param userIds
     * @param flag 1新增客户数 2联系跟进次数 3跟进客户数
     * @return
     */
    List<CountDayVO> countCustomerByYear(@Param("userIds") List<Integer> userIds, @Param("flag") Integer flag);


    List<CountDayVO> countCustomerByMonth(@Param("userIds") List<Integer> userIds, @Param("year")String year, @Param("flag") Integer flag);


    List<CountDayVO> countCustomerByDay(@Param("userIds") List<Integer> userIds, @Param("month") String month, @Param("flag") Integer flag);


    /**
     * 按分类统计-城市
     * @param dto
     * @return
     */
    List<CountSortVO> countCustomerBySort1(@Param("dto")CountDTO dto);

    /**
     * 按分类统计-行业
     * @param dto
     * @return
     */
    List<CountSortVO> countCustomerBySort2(@Param("dto")CountDTO dto);

    /**
     * 按分类统计-客户意向度
     * @param dto
     * @return
     */
    List<CountSortVO> countCustomerBySort3(@Param("dto")CountDTO dto);

    /**
     * 按分类统计-客户类别
     * @param dto
     * @return
     */
    List<CountSortVO> countCustomerBySort4(@Param("dto")CountDTO dto);


    /**
     * 新增跟进次数
     * @param id
     * @return
     */
    int updateProgressNumById(@Param("id")Integer id);

    /**
     * 自动转交客户
     * @param date
     * @param  userIds
     * @return
     */
    Customer findAllByGroupId(@Param("date") String date,@Param("userIds") List<Integer> userIds);

    int updateGroupIdById(@Param("updatedGroupId")Integer updatedGroupId,@Param("id")Integer id);
}