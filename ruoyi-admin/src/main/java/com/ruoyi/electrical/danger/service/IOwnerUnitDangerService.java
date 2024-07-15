package com.ruoyi.electrical.danger.service;

import java.util.List;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 隐患数据Service接口
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
public interface IOwnerUnitDangerService {
	/**
	 * 查询隐患数据
	 * 
	 * @param id 隐患数据主键
	 * @return 隐患数据
	 */
	public OwnerUnitDanger selectOwnerUnitDangerById(Long id);

	/**
	 * 查询隐患数据列表
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 隐患数据集合
	 */
	public List<OwnerUnitDanger> selectOwnerUnitDangerList(OwnerUnitDanger ownerUnitDanger);

	public List<DictVO> selecOwnerUnitBuildingDict(Long unitId);

}
