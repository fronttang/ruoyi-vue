package com.ruoyi.electrical.template.service;

import java.util.List;

import javax.validation.Valid;

import com.ruoyi.electrical.template.domain.DetectTemplateB;
import com.ruoyi.electrical.vo.DetectTemplateBVo;

/**
 * 模板B检测关联Service接口
 * 
 * @author fronttang
 * @date 2024-07-03
 */
public interface IDetectTemplateBService {
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
	 * 批量删除模板B检测关联
	 * 
	 * @param ids 需要删除的模板B检测关联主键集合
	 * @return 结果
	 */
	public int deleteDetectTemplateBByIds(Long[] ids);

	/**
	 * 删除模板B检测关联信息
	 * 
	 * @param id 模板B检测关联主键
	 * @return 结果
	 */
	public int deleteDetectTemplateBById(Long id);

	public List<DetectTemplateB> selectDetectTemplateBByTemplateId(Long templateId);

	public int saveTemplateDetectb(@Valid DetectTemplateBVo data);
}
