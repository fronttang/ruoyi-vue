package com.ruoyi.electrical.template.service.impl;

import java.util.List;

import javax.validation.Valid;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ruoyi.electrical.template.mapper.DetectTemplateBMapper;
import com.ruoyi.electrical.template.domain.DetectTemplateB;
import com.ruoyi.electrical.template.service.IDetectTemplateBService;
import com.ruoyi.electrical.vo.DetectTemplateBVo;

/**
 * 模板B检测关联Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-03
 */
@Service
public class DetectTemplateBServiceImpl implements IDetectTemplateBService {
	@Autowired
	private DetectTemplateBMapper detectTemplateBMapper;

	/**
	 * 查询模板B检测关联
	 * 
	 * @param id 模板B检测关联主键
	 * @return 模板B检测关联
	 */
	@Override
	public DetectTemplateB selectDetectTemplateBById(Long id) {
		return detectTemplateBMapper.selectDetectTemplateBById(id);
	}

	/**
	 * 查询模板B检测关联列表
	 * 
	 * @param detectTemplateB 模板B检测关联
	 * @return 模板B检测关联
	 */
	@Override
	public List<DetectTemplateB> selectDetectTemplateBList(DetectTemplateB detectTemplateB) {
		return detectTemplateBMapper.selectDetectTemplateBList(detectTemplateB);
	}

	/**
	 * 新增模板B检测关联
	 * 
	 * @param detectTemplateB 模板B检测关联
	 * @return 结果
	 */
	@Override
	public int insertDetectTemplateB(DetectTemplateB detectTemplateB) {
		detectTemplateB.setCreateTime(DateUtils.getNowDate());
		detectTemplateB.setUpdateTime(DateUtils.getNowDate());
		return detectTemplateBMapper.insertDetectTemplateB(detectTemplateB);
	}

	/**
	 * 修改模板B检测关联
	 * 
	 * @param detectTemplateB 模板B检测关联
	 * @return 结果
	 */
	@Override
	public int updateDetectTemplateB(DetectTemplateB detectTemplateB) {
		detectTemplateB.setUpdateTime(DateUtils.getNowDate());
		return detectTemplateBMapper.updateDetectTemplateB(detectTemplateB);
	}

	/**
	 * 批量删除模板B检测关联
	 * 
	 * @param ids 需要删除的模板B检测关联主键
	 * @return 结果
	 */
	@Override
	public int deleteDetectTemplateBByIds(Long[] ids) {
		return detectTemplateBMapper.deleteDetectTemplateBByIds(ids);
	}

	/**
	 * 删除模板B检测关联信息
	 * 
	 * @param id 模板B检测关联主键
	 * @return 结果
	 */
	@Override
	public int deleteDetectTemplateBById(Long id) {
		return detectTemplateBMapper.deleteDetectTemplateBById(id);
	}

	@Override
	public List<DetectTemplateB> selectDetectTemplateBByTemplateId(Long templateId) {
		return detectTemplateBMapper.selectDetectTemplateBByTemplateId(templateId);
	}

	@Override
	@Transactional
	public int saveTemplateDetectb(DetectTemplateBVo data) {
		// 先删除全部
		detectTemplateBMapper.deleteDetectTemplateBByTemplateId(data.getTemplateId());

		List<String> views = data.getViews();
		if (!CollectionUtils.isEmpty(views)) {
			views.forEach((view) -> {
				DetectTemplateB save = new DetectTemplateB();
				save.setBid(view);
				save.setTemplateId(data.getTemplateId());
				save.setType("1");
				this.insertDetectTemplateB(save);
			});
		}

		List<String> reports = data.getReports();
		if (!CollectionUtils.isEmpty(reports)) {
			reports.forEach((report) -> {
				DetectTemplateB save = new DetectTemplateB();
				save.setBid(report);
				save.setTemplateId(data.getTemplateId());
				save.setType("2");
				this.insertDetectTemplateB(save);
			});
		}

		return 1;
	}
}
