package com.ruoyi.electrical.project.service;

import java.util.List;
import com.ruoyi.electrical.project.domain.OwnerUnitConfig;

/**
 * 业主单元消防配置Service接口
 * 
 * @author ruoyi
 * @date 2024-07-25
 */
public interface IOwnerUnitConfigService 
{
    /**
     * 查询业主单元消防配置
     * 
     * @param unitId 业主单元消防配置主键
     * @return 业主单元消防配置
     */
    public OwnerUnitConfig selectOwnerUnitConfigByUnitId(Long unitId);

    /**
     * 查询业主单元消防配置列表
     * 
     * @param ownerUnitConfig 业主单元消防配置
     * @return 业主单元消防配置集合
     */
    public List<OwnerUnitConfig> selectOwnerUnitConfigList(OwnerUnitConfig ownerUnitConfig);

    /**
     * 新增业主单元消防配置
     * 
     * @param ownerUnitConfig 业主单元消防配置
     * @return 结果
     */
    public int insertOwnerUnitConfig(OwnerUnitConfig ownerUnitConfig);

    /**
     * 修改业主单元消防配置
     * 
     * @param ownerUnitConfig 业主单元消防配置
     * @return 结果
     */
    public int updateOwnerUnitConfig(OwnerUnitConfig ownerUnitConfig);

    /**
     * 批量删除业主单元消防配置
     * 
     * @param unitIds 需要删除的业主单元消防配置主键集合
     * @return 结果
     */
    public int deleteOwnerUnitConfigByUnitIds(Long[] unitIds);

    /**
     * 删除业主单元消防配置信息
     * 
     * @param unitId 业主单元消防配置主键
     * @return 结果
     */
    public int deleteOwnerUnitConfigByUnitId(Long unitId);
}
