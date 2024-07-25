package com.ruoyi.electrical.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.project.mapper.OwnerUnitConfigMapper;
import com.ruoyi.electrical.project.domain.OwnerUnitConfig;
import com.ruoyi.electrical.project.service.IOwnerUnitConfigService;

/**
 * 业主单元消防配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-25
 */
@Service
public class OwnerUnitConfigServiceImpl implements IOwnerUnitConfigService 
{
    @Autowired
    private OwnerUnitConfigMapper ownerUnitConfigMapper;

    /**
     * 查询业主单元消防配置
     * 
     * @param unitId 业主单元消防配置主键
     * @return 业主单元消防配置
     */
    @Override
    public OwnerUnitConfig selectOwnerUnitConfigByUnitId(Long unitId)
    {
        return ownerUnitConfigMapper.selectOwnerUnitConfigByUnitId(unitId);
    }

    /**
     * 查询业主单元消防配置列表
     * 
     * @param ownerUnitConfig 业主单元消防配置
     * @return 业主单元消防配置
     */
    @Override
    public List<OwnerUnitConfig> selectOwnerUnitConfigList(OwnerUnitConfig ownerUnitConfig)
    {
        return ownerUnitConfigMapper.selectOwnerUnitConfigList(ownerUnitConfig);
    }

    /**
     * 新增业主单元消防配置
     * 
     * @param ownerUnitConfig 业主单元消防配置
     * @return 结果
     */
    @Override
    public int insertOwnerUnitConfig(OwnerUnitConfig ownerUnitConfig)
    {
        ownerUnitConfig.setCreateTime(DateUtils.getNowDate());
        return ownerUnitConfigMapper.insertOwnerUnitConfig(ownerUnitConfig);
    }

    /**
     * 修改业主单元消防配置
     * 
     * @param ownerUnitConfig 业主单元消防配置
     * @return 结果
     */
    @Override
    public int updateOwnerUnitConfig(OwnerUnitConfig ownerUnitConfig)
    {
        ownerUnitConfig.setUpdateTime(DateUtils.getNowDate());
        return ownerUnitConfigMapper.updateOwnerUnitConfig(ownerUnitConfig);
    }

    /**
     * 批量删除业主单元消防配置
     * 
     * @param unitIds 需要删除的业主单元消防配置主键
     * @return 结果
     */
    @Override
    public int deleteOwnerUnitConfigByUnitIds(Long[] unitIds)
    {
        return ownerUnitConfigMapper.deleteOwnerUnitConfigByUnitIds(unitIds);
    }

    /**
     * 删除业主单元消防配置信息
     * 
     * @param unitId 业主单元消防配置主键
     * @return 结果
     */
    @Override
    public int deleteOwnerUnitConfigByUnitId(Long unitId)
    {
        return ownerUnitConfigMapper.deleteOwnerUnitConfigByUnitId(unitId);
    }
}
