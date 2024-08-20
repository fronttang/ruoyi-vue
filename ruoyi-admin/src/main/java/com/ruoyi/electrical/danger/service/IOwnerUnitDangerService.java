package com.ruoyi.electrical.danger.service;

import java.util.List;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.vo.DictVO;
import com.ruoyi.electrical.vo.OwnerUnitDangerGroupDetailVo;

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
	public OwnerUnitDanger ownerUnitDangerById(Long id);

	/**
	 * 查询隐患数据列表
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 隐患数据集合
	 */
	public List<OwnerUnitDanger> ownerUnitDangerList(OwnerUnitDanger ownerUnitDanger);

	public List<DictVO> selecOwnerUnitBuildingDict(Long unitId);

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

	/**
	 * 新增隐患数据
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 结果
	 */
	public int insertOwnerUnitDanger(OwnerUnitDanger ownerUnitDanger);

	/**
	 * 修改隐患数据
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 结果
	 */
	public int updateOwnerUnitDanger(OwnerUnitDanger ownerUnitDanger);

	/**
	 * 批量删除隐患数据
	 * 
	 * @param ids 需要删除的隐患数据主键集合
	 * @return 结果
	 */
	public int deleteOwnerUnitDangerByIds(Long[] ids);

	/**
	 * 删除隐患数据信息
	 * 
	 * @param id 隐患数据主键
	 * @return 结果
	 */
	public int deleteOwnerUnitDangerById(Long id);

	public List<OwnerUnitDangerGroupDetailVo> ownerUnitDangerGroupList(OwnerUnitDangerGroupDetailDto data);

	public List<OwnerUnitDangerGroupDetailVo> ownerUnitBuildingDangerGroupList(OwnerUnitDangerGroupDetailDto data);

}
