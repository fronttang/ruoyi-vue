package com.ruoyi.electrical.role.mapper;

import java.util.List;

import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 检测单位Mapper接口
 * 
 * @author fronttang
 * @date 2024-06-18
 */
public interface DetectUnitMapper {
	/**
	 * 查询检测单位
	 * 
	 * @param id 检测单位主键
	 * @return 检测单位
	 */
	public DetectUnit selectDetectUnitById(Long id);

	/**
	 * 查询检测单位列表
	 * 
	 * @param detectUnit 检测单位
	 * @return 检测单位集合
	 */
	public List<DetectUnit> selectDetectUnitList(DetectUnit detectUnit);

	public Integer checkDetectUnitName(DetectUnit detectUnit);

	/**
	 * 新增检测单位
	 * 
	 * @param detectUnit 检测单位
	 * @return 结果
	 */
	public int insertDetectUnit(DetectUnit detectUnit);

	/**
	 * 修改检测单位
	 * 
	 * @param detectUnit 检测单位
	 * @return 结果
	 */
	public int updateDetectUnit(DetectUnit detectUnit);

	/**
	 * 删除检测单位
	 * 
	 * @param id 检测单位主键
	 * @return 结果
	 */
	public int deleteDetectUnitById(Long id);

	/**
	 * 批量删除检测单位
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteDetectUnitByIds(Long[] ids);

	public List<DictVO> selectDetectUnitDict();
}
