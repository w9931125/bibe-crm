package com.bibe.crm.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;import com.bibe.crm.entity.dto.FindCustomerDTO;import com.bibe.crm.entity.dto.FindCustomerGroupDTO;import com.bibe.crm.entity.po.Customer;import com.bibe.crm.entity.vo.CustomerVO;import org.apache.ibatis.annotations.Param;import java.util.Collection;import java.util.List;import java.util.Map;

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
     * 公客分组分页列表
     *
     * @param dto
     * @param page
     * @return
     */
    IPage<CustomerVO> customerGroupPageList(FindCustomerGroupDTO dto, Page page);

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
    Map<String, Object> findLikeName(@Param("name") String name);
}