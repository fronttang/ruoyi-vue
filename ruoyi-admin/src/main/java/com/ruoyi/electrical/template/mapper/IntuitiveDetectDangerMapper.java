package com.ruoyi.electrical.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;

/**
 * 检测内容隐患Mapper接口
 * 
 * @author fronttang
 * @date 2024-06-23
 */
public interface IntuitiveDetectDangerMapper {
	/**
	 * 查询检测内容隐患
	 * 
	 * @param id 检测内容隐患主键
	 * @return 检测内容隐患
	 */
	public IntuitiveDetectDanger selectIntuitiveDetectDangerById(Long id);

	/**
	 * 查询检测内容隐患列表
	 * 
	 * @param intuitiveDetectDanger 检测内容隐患
	 * @return 检测内容隐患集合
	 */
	public List<IntuitiveDetectDanger> selectIntuitiveDetectDangerList(IntuitiveDetectDanger intuitiveDetectDanger);

	/**
	 * 新增检测内容隐患
	 * 
	 * @param intuitiveDetectDanger 检测内容隐患
	 * @return 结果
	 */
	public int insertIntuitiveDetectDanger(IntuitiveDetectDanger intuitiveDetectDanger);

	/**
	 * 修改检测内容隐患
	 * 
	 * @param intuitiveDetectDanger 检测内容隐患
	 * @return 结果
	 */
	public int updateIntuitiveDetectDanger(IntuitiveDetectDanger intuitiveDetectDanger);

	/**
	 * 删除检测内容隐患
	 * 
	 * @param id 检测内容隐患主键
	 * @return 结果
	 */
	public int deleteIntuitiveDetectDangerById(Long id);

	/**
	 * 批量删除检测内容隐患
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteIntuitiveDetectDangerByIds(Long[] ids);

	public List<IntuitiveDetectDanger> selectIntuitiveDetectDangersByDataId(Long dataId);

	public int deleteIntuitiveDetectDangerByDataId(Long dataId);

	public int deleteIntuitiveDetectDangerByDataIds(Long[] dataId);

	public Long countDangersByDataIdAndUnitId(@Param("dataId") Long dataId, @Param("unitId") Long unitId);

	public int deleteIntuitiveDetectDangerByTemplateId(@Param("templateId") Long templateId);
}
