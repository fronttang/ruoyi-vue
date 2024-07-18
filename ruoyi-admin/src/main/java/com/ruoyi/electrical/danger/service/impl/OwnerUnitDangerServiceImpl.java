package com.ruoyi.electrical.danger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
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
	public OwnerUnitDanger ownerUnitDangerById(Long id) {
		return ownerUnitDangerMapper.ownerUnitDangerById(id);
	}

	/**
	 * 查询隐患数据列表
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 隐患数据
	 */
	@Override
	public List<OwnerUnitDanger> ownerUnitDangerList(OwnerUnitDanger ownerUnitDanger) {
		return ownerUnitDangerMapper.ownerUnitDangerList(ownerUnitDanger);
	}

	@Override
	public List<DictVO> selecOwnerUnitBuildingDict(Long unitId) {
		return ownerUnitDangerMapper.selecOwnerUnitBuildingDict(unitId);
	}

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

	/**
	 * 新增隐患数据
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 结果
	 */
	@Override
	public int insertOwnerUnitDanger(OwnerUnitDanger ownerUnitDanger) {
		ownerUnitDanger.setCreateTime(DateUtils.getNowDate());
		return ownerUnitDangerMapper.insertOwnerUnitDanger(ownerUnitDanger);
	}

	/**
	 * 修改隐患数据
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 结果
	 */
	@Override
	public int updateOwnerUnitDanger(OwnerUnitDanger ownerUnitDanger) {
		ownerUnitDanger.setUpdateTime(DateUtils.getNowDate());
		return ownerUnitDangerMapper.updateOwnerUnitDanger(ownerUnitDanger);
	}

	/**
	 * 批量删除隐患数据
	 * 
	 * @param ids 需要删除的隐患数据主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitDangerByIds(Long[] ids) {
		return ownerUnitDangerMapper.deleteOwnerUnitDangerByIds(ids);
	}

	/**
	 * 删除隐患数据信息
	 * 
	 * @param id 隐患数据主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitDangerById(Long id) {
		return ownerUnitDangerMapper.deleteOwnerUnitDangerById(id);
	}

}
