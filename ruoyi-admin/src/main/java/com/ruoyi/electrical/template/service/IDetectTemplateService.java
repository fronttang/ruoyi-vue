package com.ruoyi.electrical.template.service;

import java.util.List;
import com.ruoyi.electrical.template.domain.DetectTemplate;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 模板列表Service接口
 * 
 * @author fronttang
 * @date 2024-06-24
 */
public interface IDetectTemplateService 
{
    /**
     * 查询模板列表
     * 
     * @param id 模板列表主键
     * @return 模板列表
     */
    public DetectTemplate selectDetectTemplateById(Long id);

    /**
     * 查询模板列表列表
     * 
     * @param detectTemplate 模板列表
     * @return 模板列表集合
     */
    public List<DetectTemplate> selectDetectTemplateList(DetectTemplate detectTemplate);

    /**
     * 新增模板列表
     * 
     * @param detectTemplate 模板列表
     * @return 结果
     */
    public int insertDetectTemplate(DetectTemplate detectTemplate);

    /**
     * 修改模板列表
     * 
     * @param detectTemplate 模板列表
     * @return 结果
     */
    public int updateDetectTemplate(DetectTemplate detectTemplate);

    /**
     * 批量删除模板列表
     * 
     * @param ids 需要删除的模板列表主键集合
     * @return 结果
     */
    public int deleteDetectTemplateByIds(Long[] ids);

    /**
     * 删除模板列表信息
     * 
     * @param id 模板列表主键
     * @return 结果
     */
    public int deleteDetectTemplateById(Long id);

	public List<DictVO> selectDetectTemplateDict();

	public List<DictVO> queryDetectTemplateDict(DetectTemplate detectTemplate);
}
