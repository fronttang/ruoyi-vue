package com.ruoyi.electrical.project.service;

import java.util.List;
import com.ruoyi.electrical.project.domain.OwnerUnit;

/**
 * 业主单元Service接口
 * 
 * @author fronttang
 * @date 2024-06-21
 */
public interface IOwnerUnitService {
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
	 * 批量删除业主单元
	 * 
	 * @param ids 需要删除的业主单元主键集合
	 * @return 结果
	 */
	public int deleteOwnerUnitByIds(Long[] ids);

	/**
	 * 删除业主单元信息
	 * 
	 * @param id 业主单元主键
	 * @return 结果
	 */
	public int deleteOwnerUnitById(Long id);

	public boolean startRounds(Long unitId);

	public int checkOwnerUnitName(OwnerUnit ownerUnit);

	public void setGridman(Integer type, Long gridman, Long[] unitIds);
}
