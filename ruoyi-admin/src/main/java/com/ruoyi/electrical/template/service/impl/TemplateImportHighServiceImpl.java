package com.ruoyi.electrical.template.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.dto.TemplateImportHighForm;
import com.ruoyi.electrical.template.dto.TemplateImportHighFormData;
import com.ruoyi.electrical.template.dto.TemplateImportHighFormDataDanger;
import com.ruoyi.electrical.template.dto.TemplateImportHighFormSubData;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectService;
import com.ruoyi.electrical.template.service.ITemplateImportHighService;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class TemplateImportHighServiceImpl implements ITemplateImportHighService {

	@Autowired
	private IIntuitiveDetectService intuitiveDetectService;

	@Autowired
	private IIntuitiveDetectDataService detectDataService;

	@Autowired
	private ISysDictTypeService dictTypeService;

	@Autowired
	private IIntuitiveDetectDangerService detectDangerService;

	@Override
	@Transactional
	public boolean saveFormData(Long templateId, String unitType, List<TemplateImportHighForm> formList,
			boolean delete) {

		if (delete) {
			intuitiveDetectService.deleteIntuitiveDetectByTemplateIdAndUnitType(templateId, unitType);
			detectDataService.deleteIntuitiveDetectDataByTemplateIdAndUnitType(templateId, unitType);
			// detectDangerService.ded
		}
		// 构建计分模块
		Map<String, String> dangerLevelMap = new HashMap<String, String>();
		List<SysDictData> dangerLevels = dictTypeService.selectDictDataByType("hazard_level_high");
		if (CollUtil.isNotEmpty(dangerLevels)) {
			dangerLevelMap = dangerLevels.stream()
					.collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));
		}

		String userId = String.valueOf(SecurityUtils.getUserId());

		int index = 1;
		// int scIndex = 1;
		for (TemplateImportHighForm form : formList) {

			// 计分模块1级
			IntuitiveDetectData firstData = new IntuitiveDetectData();
			firstData.setCreateBy(userId);
			firstData.setCreateTime(new Date());
			firstData.setUnitType(unitType);
			firstData.setDetectTitle(0L);
			firstData.setModuleType("1");// 1级
			firstData.setMaxScore(StrUtil.isNotBlank(form.getMaxScore()) ? Long.valueOf(form.getMaxScore()) : 0);
			firstData.setFirstCode(String.valueOf(index));
			firstData.setFirstContent(form.getName());
			firstData.setTemplateId(templateId);
			firstData.setType("2");
			firstData.setView("2");

			detectDataService.insertIntuitiveDetectData(firstData);

			// 展示模块 ，检测表
			IntuitiveDetect detect = new IntuitiveDetect();
			detect.setCreateBy(userId);
			detect.setCreateTime(new Date());
			detect.setUnitType(unitType);
			detect.setName(form.getName());
			detect.setTemplateId(templateId);
			detect.setType("A");
			detect.setCode("A" + index);

			intuitiveDetectService.insertIntuitiveDetect(detect);

			List<TemplateImportHighFormData> formDatas = form.getDatas();

			if (CollUtil.isNotEmpty(formDatas)) {

				for (TemplateImportHighFormData formData : formDatas) {

					IntuitiveDetectData viewData = new IntuitiveDetectData();
					viewData.setCreateBy(userId);
					viewData.setCreateTime(new Date());
					viewData.setUnitType(unitType);
					viewData.setDetectTitle(detect.getId());
					viewData.setMaxScore(0L);

					String content = formData.getFirstContent();
					String code = StrUtil.subBefore(content, ".", false);
					code = StrUtil.subBefore(code, "．", false);
					viewData.setFirstCode(code);
					viewData.setFirstContent(content);
					viewData.setTemplateId(templateId);
					viewData.setType("2");
					viewData.setView("1");

					detectDataService.insertIntuitiveDetectData(viewData);

					List<TemplateImportHighFormSubData> subDatas = formData.getSubDatas();

					if (CollUtil.isNotEmpty(subDatas)) {
						for (TemplateImportHighFormSubData subData : subDatas) {

							// 计分模块二级
							IntuitiveDetectData scData = new IntuitiveDetectData();
							scData.setCreateBy(userId);
							scData.setCreateTime(new Date());
							scData.setUnitType(unitType);
							scData.setDetectTitle(0L);
							scData.setModuleType("2");// 1级
							scData.setParentId(firstData.getId());
							scData.setMaxScore(
									StrUtil.isNotBlank(subData.getMaxScore()) ? Long.valueOf(subData.getMaxScore())
											: 0);
							String firstContent = subData.getFirstContent();
							String firstCode = StrUtil.subBefore(firstContent, ".", false);
							firstCode = StrUtil.subBefore(firstCode, "．", false);
							scData.setFirstCode(firstCode);
							scData.setFirstContent(firstContent);
							scData.setTemplateId(templateId);
							scData.setType("2");
							scData.setView("2");
							scData.setViewParentId(viewData.getId());

							detectDataService.insertIntuitiveDetectData(scData);

							List<TemplateImportHighFormDataDanger> dangers = subData.getDangers();

							if (CollUtil.isNotEmpty(dangers)) {
								for (TemplateImportHighFormDataDanger dataDanger : dangers) {

									IntuitiveDetectDanger danger = new IntuitiveDetectDanger();
									danger.setCreateBy(userId);
									danger.setCreateTime(new Date());
									danger.setAccMethod(StrUtil.isNotBlank(subData.getAccMethod()) ? "2" : "1");
									danger.setDataId(scData.getId());
									danger.setTemplateId(templateId);
									danger.setDescription(dataDanger.getDescription());
									danger.setSuggestions(dataDanger.getSuggestions());
									danger.setScore(StrUtil.isNotBlank(dataDanger.getScore())
											? Long.valueOf(dataDanger.getScore())
											: 0);
									danger.setLevel(dangerLevelMap.get(dataDanger.getLevel()));

									detectDangerService.insertIntuitiveDetectDanger(danger);
								}
							}

							// ++scIndex;
						}
					}
				}
			}
			++index;
		}
		return true;
	}
}
