package com.ruoyi.electrical.danger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerMapper;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerService;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 隐患数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
@Service
public class OwnerUnitDangerServiceImpl implements IOwnerUnitDangerService {
	@Autowired
	private OwnerUnitDangerMapper ownerUnitDangerMapper;

	/**
	 * 查询隐患数据
	 * 
	 * @param id 隐患数据主键
	 * @return 隐患数据
	 */
	@Override
	public OwnerUnitDanger selectOwnerUnitDangerById(Long id) {
		return ownerUnitDangerMapper.selectOwnerUnitDangerById(id);
	}

	/**
	 * 查询隐患数据列表
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 隐患数据
	 */
	@Override
	public List<OwnerUnitDanger> selectOwnerUnitDangerList(OwnerUnitDanger ownerUnitDanger) {
		return ownerUnitDangerMapper.selectOwnerUnitDangerList(ownerUnitDanger);
	}

	@Override
	public List<DictVO> selecOwnerUnitBuildingDict(Long unitId) {
		return ownerUnitDangerMapper.selecOwnerUnitBuildingDict(unitId);
	}

}
