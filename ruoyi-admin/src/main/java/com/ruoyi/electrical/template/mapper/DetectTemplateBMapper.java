package com.ruoyi.electrical.template.mapper;

import java.util.List;
import com.ruoyi.electrical.template.domain.DetectTemplateB;

/**
 * 模板B检测关联Mapper接口
 * 
 * @author fronttang
 * @date 2024-07-03
 */
public interface DetectTemplateBMapper {
	/**
	 * 查询模板B检测关联
	 * 
	 * @param id 模板B检测关联主键
	 * @return 模板B检测关联
	 */
	public DetectTemplateB selectDetectTemplateBById(Long id);

	/**
	 * 查询模板B检测关联列表
	 * 
	 * @param detectTemplateB 模板B检测关联
	 * @return 模板B检测关联集合
	 */
	public List<DetectTemplateB> selectDetectTemplateBList(DetectTemplateB detectTemplateB);

	/**
	 * 新增模板B检测关联
	 * 
	 * @param detectTemplateB 模板B检测关联
	 * @return 结果
	 */
	public int insertDetectTemplateB(DetectTemplateB detectTemplateB);

	/**
	 * 修改模板B检测关联
	 * 
	 * @param detectTemplateB 模板B检测关联
	 * @return 结果
	 */
	public int updateDetectTemplateB(DetectTemplateB detectTemplateB);

	/**
	 * 删除模板B检测关联
	 * 
	 * @param id 模板B检测关联主键
	 * @return 结果
	 */
	public int deleteDetectTemplateBById(Long id);

	/**
	 * 批量删除模板B检测关联
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteDetectTemplateBByIds(Long[] ids);

	public List<DetectTemplateB> selectDetectTemplateBByTemplateId(Long templateId);

	public int deleteDetectTemplateBByTemplateId(Long templateId);
}
