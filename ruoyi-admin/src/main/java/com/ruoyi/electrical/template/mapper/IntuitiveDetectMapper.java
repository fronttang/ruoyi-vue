package com.ruoyi.electrical.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.dto.IntuitiveDetectQuery;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 直观检测标题Mapper接口
 * 
 * @author fronttang
 * @date 2024-06-19
 */
public interface IntuitiveDetectMapper {
	/**
	 * 查询直观检测标题
	 * 
	 * @param id 直观检测标题主键
	 * @return 直观检测标题
	 */
	public IntuitiveDetect selectIntuitiveDetectById(Long id);

	/**
	 * 查询直观检测标题列表
	 * 
	 * @param intuitiveDetect 直观检测标题
	 * @return 直观检测标题集合
	 */
	public List<IntuitiveDetect> selectIntuitiveDetectList(IntuitiveDetectQuery intuitiveDetect);

	/**
	 * 新增直观检测标题
	 * 
	 * @param intuitiveDetect 直观检测标题
	 * @return 结果
	 */
	public int insertIntuitiveDetect(IntuitiveDetect intuitiveDetect);

	/**
	 * 修改直观检测标题
	 * 
	 * @param intuitiveDetect 直观检测标题
	 * @return 结果
	 */
	public int updateIntuitiveDetect(IntuitiveDetect intuitiveDetect);

	/**
	 * 删除直观检测标题
	 * 
	 * @param id 直观检测标题主键
	 * @return 结果
	 */
	public int deleteIntuitiveDetectById(Long id);

	/**
	 * 批量删除直观检测标题
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteIntuitiveDetectByIds(Long[] ids);

	/**
	 * 直观检测标题字典
	 * 
	 * @return
	 */
	public List<DictVO> selectIntuitiveDetectDict(Long templateId);

	public List<DictVO> selectIntuitiveDetectListDict(IntuitiveDetect intuitiveDetect);

	public int deleteIntuitiveDetectByTemplateIdAndUnitType(@Param("templateId") Long templateId,
			@Param("unitType") String unitType);
}
