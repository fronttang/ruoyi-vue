package com.ruoyi.electrical.danger.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.electrical.danger.controller.DangerExcelExportStylerImpl;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.handler.IFormbDangerHandler;
import com.ruoyi.electrical.dto.DangerExportIndustrialAreaDto;
import com.ruoyi.electrical.dto.DangerExportIndustrialAreaDto.DangerExportIndustrialAreaDangerDto;
import com.ruoyi.electrical.dto.DangerExportUrbanVillageQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class OwnerUnitDangerExportIndustrialService {

	@Autowired
	private IOwnerUnitDangerExportService dangerExportService;

	public List<Map<String, Object>> exportDanger(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportUrbanVillageQueryDto> exportData = new ArrayList<DangerExportUrbanVillageQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportUrbanVillageByUnitId(data.getIds()));
		} else {
			exportData.addAll(dangerExportService.exportUrbanVillageByQuery(data));
		}

		List<DangerExportIndustrialAreaDto> datas = buildUrbanVillageDto(exportData);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		ExportParams exportParams = new ExportParams();
		// rentalHouseParams.setCreateHeadRows(false);
		exportParams.setSheetName("隐患台账");
		exportParams.setTitle("工业园区电气检测隐患台账");
		exportParams.setStyle(DangerExcelExportStylerImpl.class);

		Map<String, Object> rentalHouseMap = new HashMap<String, Object>();
		rentalHouseMap.put("title", exportParams);
		rentalHouseMap.put("data", datas);
		rentalHouseMap.put("entity", DangerExportIndustrialAreaDto.class);
		list.add(rentalHouseMap);

		return list;
	}

	private List<DangerExportIndustrialAreaDto> buildUrbanVillageDto(
			List<DangerExportUrbanVillageQueryDto> exportData) {

		Long index = 1L;
		List<DangerExportIndustrialAreaDto> result = new ArrayList<DangerExportIndustrialAreaDto>();
		for (DangerExportUrbanVillageQueryDto data : exportData) {
			DangerExportIndustrialAreaDto export = new DangerExportIndustrialAreaDto();
			BeanUtils.copyProperties(data, export);
			export.setId(index++);

			List<OwnerUnitDanger> dangers = data.getDangers();

			// 排查检测日期转换
			List<String> detectDate = new ArrayList<String>();
			if (Objects.nonNull(data.getTestStartDate())) {
				detectDate.add(DateUtil.format(data.getTestStartDate(), DatePattern.NORM_DATE_PATTERN));
			}
			if (Objects.nonNull(data.getTestEndDate())) {
				detectDate.add(DateUtil.format(data.getTestEndDate(), DatePattern.NORM_DATE_PATTERN));
			}
			export.setDetectDate(String.join("~", detectDate));

			// 隐患总数量
			long danger = dangers.stream().count();
			export.setDanger(danger);

			// A类隐患数量
			long dangersA = dangers.stream().filter((d) -> "A".equalsIgnoreCase(d.getLevel())).count();
			export.setDangersA(dangersA);

			// B类隐患数量
			long dangersB = dangers.stream().filter((d) -> "B".equalsIgnoreCase(d.getLevel())).count();
			export.setDangersB(dangersB);

			// C类隐患数量
			long dangersC = dangers.stream().filter((d) -> "C".equalsIgnoreCase(d.getLevel())).count();
			export.setDangersC(dangersC);

			List<DangerExportIndustrialAreaDangerDto> exportDangers = new ArrayList<DangerExportIndustrialAreaDangerDto>();

			if (CollUtil.isNotEmpty(dangers)) {
				// 隐患分组
				Map<String, List<OwnerUnitDanger>> dangerGroupMap = dangers.stream()
						.filter((d) -> StrUtil.isNotBlank(d.getDescription()))
						.filter((d) -> !"B".equalsIgnoreCase(d.getFormType()) || ("B".equalsIgnoreCase(d.getFormType())
								&& IFormbDangerHandler.FAILURE.equalsIgnoreCase(d.getResult())))
						.collect(Collectors.groupingBy(OwnerUnitDanger::getDescription, Collectors.toList()));

				buildExportDanger(exportDangers, dangerGroupMap);
			}

			export.setDangers(exportDangers);
			result.add(export);
		}
		return result;
	}

	private void buildExportDanger(List<DangerExportIndustrialAreaDangerDto> exportDangers,
			Map<String, List<OwnerUnitDanger>> dangerGroupMap) {
		if (CollUtil.isNotEmpty(dangerGroupMap)) {

			Set<Entry<String, List<OwnerUnitDanger>>> entrySet = dangerGroupMap.entrySet();
			Iterator<Entry<String, List<OwnerUnitDanger>>> iterator = entrySet.iterator();

			int index = 1;
			while (iterator.hasNext()) {
				Entry<String, List<OwnerUnitDanger>> next = iterator.next();
				// Long key = next.getKey();
				List<OwnerUnitDanger> dangerList = next.getValue();
				if (CollUtil.isNotEmpty(dangerList)) {
					DangerExportIndustrialAreaDangerDto exportDanger = new DangerExportIndustrialAreaDangerDto();
					OwnerUnitDanger oud = dangerList.get(0);

					List<String> locations = dangerList.stream().map((d) -> d.getReportLocation())
							.filter((d) -> StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());

					exportDanger.setLevel(oud.getLevel());
					exportDanger.setNo(String.valueOf(index++));
					exportDanger.setSuggestions(oud.getSuggestions());
					exportDanger.setDescription(oud.getDescription());
					exportDanger.setLocation(String.join("、", locations));

					List<String> pics = dangerList.stream().filter((d) -> StrUtil.isNotBlank(d.getDangerPic()))
							.map((d) -> d.getDangerPic()).flatMap(str -> Arrays.stream(str.split(",")))
							.collect(Collectors.toList());

					if (CollUtil.isNotEmpty(pics)) {
						exportDanger.setDangerPic(readFileByte(pics.get(0)));
					}

					exportDangers.add(exportDanger);
				}
			}
		}
	}

	private byte[] readFileByte(String pic) {
		try {
			// 本地资源路径
			String localPath = RuoYiConfig.getProfile();
			// 数据库资源地址
			String filePath = localPath + StringUtils.substringAfter(pic, Constants.RESOURCE_PREFIX);
			return FileUtil.readBytes(filePath);
		} catch (Exception e) {

		}
		return null;
	}
}
