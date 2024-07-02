package com.ruoyi.electrical.template.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.template.mapper.DetectTemplateMapper;
import com.ruoyi.electrical.template.domain.DetectTemplate;
import com.ruoyi.electrical.template.service.IDetectTemplateService;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 模板列表Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-24
 */
@Service
public class DetectTemplateServiceImpl implements IDetectTemplateService 
{
    @Autowired
    private DetectTemplateMapper detectTemplateMapper;

    /**
     * 查询模板列表
     * 
     * @param id 模板列表主键
     * @return 模板列表
     */
    @Override
    public DetectTemplate selectDetectTemplateById(Long id)
    {
        return detectTemplateMapper.selectDetectTemplateById(id);
    }

    /**
     * 查询模板列表列表
     * 
     * @param detectTemplate 模板列表
     * @return 模板列表
     */
    @Override
    public List<DetectTemplate> selectDetectTemplateList(DetectTemplate detectTemplate)
    {
        return detectTemplateMapper.selectDetectTemplateList(detectTemplate);
    }

    /**
     * 新增模板列表
     * 
     * @param detectTemplate 模板列表
     * @return 结果
     */
    @Override
    public int insertDetectTemplate(DetectTemplate detectTemplate)
    {
        detectTemplate.setCreateTime(DateUtils.getNowDate());
        detectTemplate.setUpdateTime(DateUtils.getNowDate());
        return detectTemplateMapper.insertDetectTemplate(detectTemplate);
    }

    /**
     * 修改模板列表
     * 
     * @param detectTemplate 模板列表
     * @return 结果
     */
    @Override
    public int updateDetectTemplate(DetectTemplate detectTemplate)
    {
        detectTemplate.setUpdateTime(DateUtils.getNowDate());
        return detectTemplateMapper.updateDetectTemplate(detectTemplate);
    }

    /**
     * 批量删除模板列表
     * 
     * @param ids 需要删除的模板列表主键
     * @return 结果
     */
    @Override
    public int deleteDetectTemplateByIds(Long[] ids)
    {
        return detectTemplateMapper.deleteDetectTemplateByIds(ids);
    }

    /**
     * 删除模板列表信息
     * 
     * @param id 模板列表主键
     * @return 结果
     */
    @Override
    public int deleteDetectTemplateById(Long id)
    {
        return detectTemplateMapper.deleteDetectTemplateById(id);
    }

	@Override
	public List<DictVO> selectDetectTemplateDict() {
		return detectTemplateMapper.selectDetectTemplateDict();
	}

	@Override
	public List<DictVO> queryDetectTemplateDict(DetectTemplate detectTemplate) {
		return detectTemplateMapper.queryDetectTemplateDict(detectTemplate);
	}
}
