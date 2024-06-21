package com.ruoyi.electrical.project.mapper;

import java.util.List;
import com.ruoyi.electrical.project.domain.OwnerUnit;

/**
 * 业主单元Mapper接口
 * 
 * @author fronttang
 * @date 2024-06-21
 */
public interface OwnerUnitMapper 
{
    /**
     * 查询业主单元
     * 
     * @param id 业主单元主键
     * @return 业主单元
     */
    public OwnerUnit selectOwnerUnitById(Long id);

    /**
     * 查询业主单元列表
     * 
     * @param ownerUnit 业主单元
     * @return 业主单元集合
     */
    public List<OwnerUnit> selectOwnerUnitList(OwnerUnit ownerUnit);

    /**
     * 新增业主单元
     * 
     * @param ownerUnit 业主单元
     * @return 结果
     */
    public int insertOwnerUnit(OwnerUnit ownerUnit);

    /**
     * 修改业主单元
     * 
     * @param ownerUnit 业主单元
     * @return 结果
     */
    public int updateOwnerUnit(OwnerUnit ownerUnit);

    /**
     * 删除业主单元
     * 
     * @param id 业主单元主键
     * @return 结果
     */
    public int deleteOwnerUnitById(Long id);

    /**
     * 批量删除业主单元
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOwnerUnitByIds(Long[] ids);
}
