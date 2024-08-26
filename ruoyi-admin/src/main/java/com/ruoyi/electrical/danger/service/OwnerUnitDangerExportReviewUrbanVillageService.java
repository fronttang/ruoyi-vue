package com.ruoyi.electrical.danger.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

import com.ruoyi.electrical.danger.controller.DangerExcelExportStylerImpl;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.handler.IFormbDangerHandler;
import com.ruoyi.electrical.dto.DangerExportUrbanVillageQueryDto;
import com.ruoyi.electrical.dto.DangerReviewExportUrbanVillageDto;
import com.ruoyi.electrical.dto.DangerReviewExportUrbanVillageDto.DangerReivewExportUrbanVillageDangerDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class OwnerUnitDangerExportReviewUrbanVillageService {

	@Autowired
	private IOwnerUnitDangerExportService dangerExportService;

	public List<Map<String, Object>> exportDanger(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportUrbanVillageQueryDto> exportData = new ArrayList<DangerExportUrbanVillageQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportUrbanVillageByUnitId(data.getIds()));
		} else {
			exportData.addAll(dangerExportService.exportUrbanVillageByQuery(data));
		}

		List<DangerReviewExportUrbanVillageDto> datas = buildUrbanVillageDto(exportData);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		ExportParams exportParams = new ExportParams();
		// rentalHouseParams.setCreateHeadRows(false);
		exportParams.setSheetName("隐患台账");
		exportParams.setTitle("城中村出租屋用电安全隐患台账明细表");
		exportParams.setStyle(DangerExcelExportStylerImpl.class);

		Map<String, Object> rentalHouseMap = new HashMap<String, Object>();
		rentalHouseMap.put("title", exportParams);
		rentalHouseMap.put("data", datas);
		rentalHouseMap.put("entity", DangerReviewExportUrbanVillageDto.class);
		list.add(rentalHouseMap);

		return list;
	}

	private List<DangerReviewExportUrbanVillageDto> buildUrbanVillageDto(
			List<DangerExportUrbanVillageQueryDto> exportData) {

		Long index = 1L;
		List<DangerReviewExportUrbanVillageDto> result = new ArrayList<DangerReviewExportUrbanVillageDto>();
		for (DangerExportUrbanVillageQueryDto data : exportData) {
			DangerReviewExportUrbanVillageDto export = new DangerReviewExportUrbanVillageDto();
			BeanUtils.copyProperties(data, export);
			export.setId(index++);

			List<OwnerUnitDanger> dangers = data.getDangers();

			// 复查检测日期
			if (Objects.nonNull(data.getReviewDate())) {
				export.setDetectDate(DateUtil.format(data.getReviewDate(), DatePattern.NORM_DATE_PATTERN));
			}

			Long detectDoorNumber = data.getAreas();
			// 入户检测房间数量
			export.setDetectDoorNumber(detectDoorNumber);
			// 入户率
			if (Objects.nonNull(detectDoorNumber) && detectDoorNumber > 0 && Objects.nonNull(data.getDoorNumber())
					&& data.getDoorNumber() > 0) {
				BigDecimal rate = new BigDecimal(detectDoorNumber).divide(new BigDecimal(data.getDoorNumber()), 8,
						BigDecimal.ROUND_HALF_UP);
				rate = new BigDecimal(100).multiply(rate);
				rate = rate.setScale(2, RoundingMode.HALF_UP);
				export.setHouseholdRate(StrUtil.format("{}%", rate.toPlainString()));
			} else {
				export.setHouseholdRate("0%");
			}
			// 隐患已整改数量
			long finishs = dangers.stream().filter((d) -> "2".equalsIgnoreCase(d.getStatus())).count();
			export.setFinishs(finishs);

			// 隐患须整改数量
			long unFinishs = dangers.stream().filter((d) -> !"2".equalsIgnoreCase(d.getStatus())).count();
			export.setUnFinishs(unFinishs);

			// 隐患总数量
			long danger = dangers.stream().count();
			export.setDanger(danger);

			// 整改率
			if (finishs > 0 && danger > 0) {
				BigDecimal rate = new BigDecimal(finishs).divide(new BigDecimal(danger), 8, BigDecimal.ROUND_HALF_UP);
				rate = new BigDecimal(100).multiply(rate);
				rate = rate.setScale(2, RoundingMode.HALF_UP);
				export.setCorrectionRate(StrUtil.format("{}%", rate.toPlainString()));
			} else {
				export.setCorrectionRate("0%");
			}

			// A类隐患数量
			long dangersA = dangers.stream().filter((d) -> "A".equalsIgnoreCase(d.getLevel())).count();
			export.setDangersA(dangersA);

			// B类隐患数量
			long dangersB = dangers.stream().filter((d) -> "B".equalsIgnoreCase(d.getLevel())).count();
			export.setDangersB(dangersB);

			// C类隐患数量
			long dangersC = dangers.stream().filter((d) -> "C".equalsIgnoreCase(d.getLevel())).count();
			export.setDangersC(dangersC);

			// 公共区域隐患数量
			long dangerAreaType1 = dangers.stream().filter((d) -> "1".equalsIgnoreCase(d.getAreaType())).count();
			export.setDangerAreaType1(dangerAreaType1);

			// 户内隐患数量
			long dangerAreaType2 = dangers.stream().filter((d) -> "2".equalsIgnoreCase(d.getAreaType())).count();
			export.setDangerAreaType2(dangerAreaType2);

			List<DangerReivewExportUrbanVillageDangerDto> exportDangers = new ArrayList<DangerReivewExportUrbanVillageDangerDto>();

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

	private void buildExportDanger(List<DangerReivewExportUrbanVillageDangerDto> exportDangers,
			Map<String, List<OwnerUnitDanger>> dangerGroupMap) {
		if (CollUtil.isNotEmpty(dangerGroupMap)) {

			Set<Entry<String, List<OwnerUnitDanger>>> entrySet = dangerGroupMap.entrySet();
			Iterator<Entry<String, List<OwnerUnitDanger>>> iterator = entrySet.iterator();

			int dangerIndex = 1;

			while (iterator.hasNext()) {
				Entry<String, List<OwnerUnitDanger>> next = iterator.next();
				// Long key = next.getKey();
				List<OwnerUnitDanger> dangerList = next.getValue();
				if (CollUtil.isNotEmpty(dangerList)) {
					DangerReivewExportUrbanVillageDangerDto exportDanger = new DangerReivewExportUrbanVillageDangerDto();
					OwnerUnitDanger oud = dangerList.get(0);

					List<String> locations = dangerList.stream().map((d) -> d.getReportLocation())
							.filter((d) -> StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());

					exportDanger.setLevel(oud.getLevel());
					exportDanger.setNo(String.valueOf(dangerIndex++));
					exportDanger.setSuggestions(oud.getSuggestions());
					exportDanger
							.setDescription(StrUtil.format("{}{}", String.join("、", locations), oud.getDescription()));

					// 已整改隐患区域
					List<String> finishLocations = dangerList.stream()
							.filter((d) -> "2".equalsIgnoreCase(d.getStatus())).map((d) -> d.getReportLocation())
							.filter((d) -> StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());
					exportDanger.setFinishLocation(String.join("、", finishLocations));

					// 须整改隐患区域
					List<String> unFinishLocations = dangerList.stream()
							.filter((d) -> !"2".equalsIgnoreCase(d.getStatus())).map((d) -> d.getReportLocation())
							.filter((d) -> StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());
					exportDanger.setUnFinishLocation(String.join("、", unFinishLocations));

					// 复查区域
					List<String> reivewLocations = dangerList.stream()
							.filter((d) -> "1".equalsIgnoreCase(d.getStatus())).map((d) -> d.getReportLocation())
							.filter((d) -> StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());
					exportDanger.setReviewLocation(String.join("、", reivewLocations));

					// 整改情况
					long finishDangers = dangerList.stream().filter((d) -> "2".equalsIgnoreCase(d.getStatus())).count();
					if (finishDangers == dangerList.size()) {
						exportDanger.setReivewStatus("已整改");
					} else {
						exportDanger.setReivewStatus("未整改");
					}

					exportDangers.add(exportDanger);
				}
			}
		}
	}
}
