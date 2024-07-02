package com.ruoyi.electrical.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.project.mapper.OwnerUnitMapper;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.service.IOwnerUnitService;

/**
 * 业主单元Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-21
 */
@Service
public class OwnerUnitServiceImpl implements IOwnerUnitService {
	@Autowired
	private OwnerUnitMapper ownerUnitMapper;

	/**
	 * 查询业主单元
	 * 
	 * @param id 业主单元主键
	 * @return 业主单元
	 */
	@Override
	public OwnerUnit selectOwnerUnitById(Long id) {
		return ownerUnitMapper.selectOwnerUnitById(id);
	}

	/**
	 * 查询业主单元列表
	 * 
	 * @param ownerUnit 业主单元
	 * @return 业主单元
	 */
	@Override
	public List<OwnerUnit> selectOwnerUnitList(OwnerUnit ownerUnit) {
		return ownerUnitMapper.selectOwnerUnitList(ownerUnit);
	}

	/**
	 * 新增业主单元
	 * 
	 * @param ownerUnit 业主单元
	 * @return 结果
	 */
	@Override
	public int insertOwnerUnit(OwnerUnit ownerUnit) {
		ownerUnit.setCreateTime(DateUtils.getNowDate());
		ownerUnit.setUpdateTime(DateUtils.getNowDate());
		return ownerUnitMapper.insertOwnerUnit(ownerUnit);
	}

	/**
	 * 修改业主单元
	 * 
	 * @param ownerUnit 业主单元
	 * @return 结果
	 */
	@Override
	public int updateOwnerUnit(OwnerUnit ownerUnit) {
		ownerUnit.setUpdateTime(DateUtils.getNowDate());
		return ownerUnitMapper.updateOwnerUnit(ownerUnit);
	}

	/**
	 * 批量删除业主单元
	 * 
	 * @param ids 需要删除的业主单元主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitByIds(Long[] ids) {
		return ownerUnitMapper.deleteOwnerUnitByIds(ids);
	}

	/**
	 * 删除业主单元信息
	 * 
	 * @param id 业主单元主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitById(Long id) {
		return ownerUnitMapper.deleteOwnerUnitById(id);
	}
}
