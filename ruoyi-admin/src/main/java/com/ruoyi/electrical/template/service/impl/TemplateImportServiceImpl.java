package com.ruoyi.electrical.template.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.dto.TemplateImportDto;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectService;
import com.ruoyi.electrical.template.service.ITemplateImportService;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class TemplateImportServiceImpl implements ITemplateImportService {

	@Autowired
	private IIntuitiveDetectDataService intuitiveDetectDataService;

	@Autowired
	private IIntuitiveDetectDangerService intuitiveDetectDangerService;

	@Autowired
	private IIntuitiveDetectService intuitiveDetectService;

	@Autowired
	private ISysDictTypeService dictTypeService;

	@Override
	public boolean saveFormData(Long templateId, List<TemplateImportDto> importDatas, boolean delete) {

		if (CollUtil.isNotEmpty(importDatas)) {

			if (delete) {
				intuitiveDetectService.deleteIntuitiveDetectByTemplateId(templateId);
				intuitiveDetectDataService.deleteIntuitiveDetectDataByTemplateId(templateId);
				intuitiveDetectDangerService.deleteIntuitiveDetectDangerByTemplateId(templateId);
			}

			Map<String, String> dangerLevelMap = new HashMap<String, String>();
			List<SysDictData> dangerLevels = dictTypeService.selectDictDataByType("hazard_level");
			if (CollUtil.isNotEmpty(dangerLevels)) {
				dangerLevelMap = dangerLevels.stream()
						.collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));
			}

			Map<String, IntuitiveDetect> formMap = new HashMap<String, IntuitiveDetect>();

			Map<String, IntuitiveDetectData> formDataMap = new HashMap<String, IntuitiveDetectData>();

			for (TemplateImportDto importData : importDatas) {

				String key = StrUtil.format("{}{}", importData.getCode(), importData.getName());

				IntuitiveDetect form = formMap.get(key);

				if (form == null) {
					form = new IntuitiveDetect();
					form.setTemplateId(templateId);
					form.setCode(importData.getCode());
					form.setName(importData.getName());
					form.setType(StrUtil.startWith(importData.getCode(), "A") ? "A"
							: StrUtil.startWith(importData.getCode(), "C") ? "C" : "");
					formMap.put(key, form);
				}

				List<String> attribution = form.getAttribution();
				if (attribution == null) {
					attribution = new ArrayList<String>();
					form.setAttribution(attribution);
				}

				if (StrUtil.isNotBlank(importData.getAttr()) && StrUtil.contains(importData.getAttr(), "公共区域")) {
					attribution.add("1");
				}
				if (StrUtil.isNotBlank(importData.getAttr()) && StrUtil.contains(importData.getAttr(), "户")) {
					attribution.add("2");
				}

				List<IntuitiveDetectData> formDatas = form.getDatas();
				if (formDatas == null) {
					formDatas = new ArrayList<IntuitiveDetectData>();
					form.setDatas(formDatas);
				}

				IntuitiveDetectData fromData = formDataMap.get(importData.getFirstCode());

				if (fromData == null) {
					fromData = new IntuitiveDetectData();
					BeanUtils.copyProperties(importData, fromData);
					fromData.setTemplateId(templateId);
					fromData.setType("标题".equalsIgnoreCase(importData.getType()) ? "1" : "2");
					fromData.setOutput("1");

					formDataMap.put(importData.getFirstCode(), fromData);
					formDatas.add(fromData);
				}

				List<IntuitiveDetectDanger> dangers = fromData.getDangers();
				if (dangers == null) {
					dangers = new ArrayList<IntuitiveDetectDanger>();
					fromData.setDangers(dangers);
				}
				IntuitiveDetectDanger danger = new IntuitiveDetectDanger();
				BeanUtils.copyProperties(importData, danger);
				danger.setTemplateId(templateId);
				danger.setLevel(dangerLevelMap.get(danger.getLevel()));
				dangers.add(danger);
			}

			Map<String, IntuitiveDetect> result = new HashMap<String, IntuitiveDetect>();

			formMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
					.forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

			result.forEach((key, form) -> {

				intuitiveDetectService.insertIntuitiveDetect(form);

				List<IntuitiveDetectData> formDatas = form.getDatas();
				if (CollUtil.isNotEmpty(formDatas)) {
					formDatas.forEach((formData) -> {
						formData.setDetectTitle(form.getId());
						intuitiveDetectDataService.insertIntuitiveDetectData(formData);
					});
				}
			});

			return true;
		}
		return false;
	}

}
