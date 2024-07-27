package com.ruoyi.electrical.danger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.report.dto.high.HighDangerInfo;
import com.ruoyi.electrical.vo.DictVO;
import com.ruoyi.electrical.vo.OwnerUnitDangerGroupDetailVo;

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
	public OwnerUnitDanger ownerUnitDangerById(Long id);

	/**
	 * 查询隐患数据列表
	 * 
	 * @param ownerUnitDanger 隐患数据
	 * @return 隐患数据集合
	 */
	public List<OwnerUnitDanger> ownerUnitDangerList(OwnerUnitDanger ownerUnitDanger);

	public List<DictVO> selecOwnerUnitBuildingDict(@Param("unitId") Long unitId);

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
	 * 删除隐患数据
	 * 
	 * @param id 隐患数据主键
	 * @return 结果
	 */
	public int deleteOwnerUnitDangerById(Long id);

	/**
	 * 批量删除隐患数据
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteOwnerUnitDangerByIds(Long[] ids);

	public List<HighDangerInfo> selectOwnerDangerHighReportByUnitId(@Param("unitId") Long unitId);

	public List<OwnerUnitDangerGroupDetailVo> ownerUnitDangerGroupList(OwnerUnitDangerGroupDetailDto data);

	public List<OwnerUnitDangerGroupDetailVo> ownerUnitBuildingDangerGroupList(OwnerUnitDangerGroupDetailDto data);

}
