package com.ruoyi.electrical.template.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.dto.TemplateImportStationDto;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;
import com.ruoyi.electrical.template.service.ITemplateImportStationService;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class TemplateImportStationServiceImpl implements ITemplateImportStationService {

	@Autowired
	private IIntuitiveDetectDataService intuitiveDetectDataService;

	@Autowired
	private IIntuitiveDetectDangerService intuitiveDetectDangerService;

	@Autowired
	private ISysDictTypeService dictTypeService;

	private static final Map<String, String> DETECT_MODULE = new HashMap<String, String>();

	static {
		DETECT_MODULE.put("整体检查", "1");
		DETECT_MODULE.put("用电检查", "2");
		DETECT_MODULE.put("消防检查", "3");
		DETECT_MODULE.put("储能检查", "4");
		DETECT_MODULE.put("场站其他检查", "5");
		DETECT_MODULE.put("充电桩检查", "6");
	}

	@Override
	@Transactional
	public boolean saveFormData(Long templateId, List<TemplateImportStationDto> importDatas, boolean delete) {

		if (CollUtil.isNotEmpty(importDatas)) {

			if (delete) {
				intuitiveDetectDataService.deleteIntuitiveDetectDataByTemplateId(templateId);
				intuitiveDetectDangerService.deleteIntuitiveDetectDangerByTemplateId(templateId);
			}

			Map<String, String> dangerLevelMap = new HashMap<String, String>();
			List<SysDictData> dangerLevels = dictTypeService.selectDictDataByType("hazard_level_charging_station");
			if (CollUtil.isNotEmpty(dangerLevels)) {
				dangerLevelMap = dangerLevels.stream()
						.collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));
			}

			Map<String, IntuitiveDetectData> formDataMap = new HashMap<String, IntuitiveDetectData>();

			for (TemplateImportStationDto importData : importDatas) {

				String key = StrUtil.format("{}{}", importData.getDetectModule(), importData.getFirstCode());

				IntuitiveDetectData fromData = formDataMap.get(key);
				if (fromData == null) {
					fromData = new IntuitiveDetectData();
					BeanUtils.copyProperties(importData, fromData);
					fromData.setAttribution(Arrays.asList(importData.getAttr()));
					fromData.setDetectModule(DETECT_MODULE.get(fromData.getDetectModule()));
					fromData.setTemplateId(templateId);
					fromData.setType("2");
					fromData.setDetectTitle(0L);

					formDataMap.put(key, fromData);
				}

				List<IntuitiveDetectDanger> dangers = fromData.getDangers();
				if (dangers == null) {
					dangers = new ArrayList<IntuitiveDetectDanger>();
					fromData.setDangers(dangers);
				}

				if (StrUtil.isBlank(importData.getDescription()) && StrUtil.isBlank(importData.getSuggestions())) {
					continue;
				}

				IntuitiveDetectDanger danger = new IntuitiveDetectDanger();
				BeanUtils.copyProperties(importData, danger);
				danger.setTemplateId(templateId);
				danger.setLevel(dangerLevelMap.get(danger.getLevel()));
				dangers.add(danger);
			}

			formDataMap.forEach((key, value) -> {
				intuitiveDetectDataService.insertIntuitiveDetectData(value);
			});

			return true;
		}
		return false;
	}

}
