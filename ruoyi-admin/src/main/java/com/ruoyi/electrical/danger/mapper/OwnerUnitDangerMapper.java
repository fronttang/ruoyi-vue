package com.ruoyi.electrical.danger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 隐患数据Mapper接口
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
public interface OwnerUnitDangerMapper {
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

	public List<DictVO> selecOwnerUnitBuildingDict(@Param("unitId") Long unitId);

}
