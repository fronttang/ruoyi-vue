package com.ruoyi.electrical.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 直观检测表内容Mapper接口
 * 
 * @author fronttang
 * @date 2024-06-23
 */
public interface IntuitiveDetectDataMapper {
	/**
	 * 查询直观检测表内容
	 * 
	 * @param id 直观检测表内容主键
	 * @return 直观检测表内容
	 */
	public IntuitiveDetectData selectIntuitiveDetectDataById(Long id);

	/**
	 * 查询直观检测表内容列表
	 * 
	 * @param intuitiveDetectData 直观检测表内容
	 * @return 直观检测表内容集合
	 */
	public List<IntuitiveDetectData> selectIntuitiveDetectDataList(IntuitiveDetectData intuitiveDetectData);

	/**
	 * 新增直观检测表内容
	 * 
	 * @param intuitiveDetectData 直观检测表内容
	 * @return 结果
	 */
	public int insertIntuitiveDetectData(IntuitiveDetectData intuitiveDetectData);

	/**
	 * 修改直观检测表内容
	 * 
	 * @param intuitiveDetectData 直观检测表内容
	 * @return 结果
	 */
	public int updateIntuitiveDetectData(IntuitiveDetectData intuitiveDetectData);

	/**
	 * 删除直观检测表内容
	 * 
	 * @param id 直观检测表内容主键
	 * @return 结果
	 */
	public int deleteIntuitiveDetectDataById(Long id);

	/**
	 * 批量删除直观检测表内容
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteIntuitiveDetectDataByIds(Long[] ids);

	public List<DictVO> selectIntuitiveDetectDataDict(IntuitiveDetectData intuitiveDetectData);

	public List<IntuitiveDetectData> selectIntuitiveDetectDataViewList(IntuitiveDetectData intuitiveDetectData);

	public IntuitiveDetectData selectIntuitiveDetectDataViewById(Long id);

	public int deleteIntuitiveDetectDataViewByIds(Long[] ids);

	public int deleteIntuitiveDetectDataByTemplateIdAndUnitType(@Param("templateId") Long templateId,
			@Param("unitType") String unitType);
}
