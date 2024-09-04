package com.ruoyi.electrical.danger.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Workbook exportDanger(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportUrbanVillageQueryDto> exportData = new ArrayList<DangerExportUrbanVillageQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportUrbanVillageByUnitId(data.getIds()));
		} else {
			exportData.addAll(dangerExportService.exportUrbanVillageByQuery(data));
		}

		List<DangerReviewExportUrbanVillageDto> datas = buildUrbanVillageDto(exportData);

		ExportParams exportParams = new ExportParams();
		// rentalHouseParams.setCreateHeadRows(false);
		exportParams.setSheetName("隐患台账");
		exportParams.setTitle("城中村出租屋用电安全隐患台账明细表");
		exportParams.setStyle(DangerExcelExportStylerImpl.class);

		Workbook workbook = new XSSFWorkbook();
		DangerReportExcelExportService service = new DangerReportExcelExportService();
		service.createSheet(workbook, exportParams, DangerReviewExportUrbanVillageDto.class, datas);

		for (Sheet sheet : workbook) {
			for (int i = 2; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				row.getCell(6).setCellStyle(getCellStyleBorder(workbook));
				row.getCell(7).setCellStyle(getCellStyleBorder(workbook));
				row.getCell(8).setCellStyle(getCellStyleLeft(workbook));
				row.getCell(9).setCellStyle(getCellStyleLeft(workbook));
				row.getCell(10).setCellStyle(getCellStyleLeft(workbook));
				row.getCell(11).setCellStyle(getCellStyleLeft(workbook));
				row.getCell(12).setCellStyle(getCellStyleLeft(workbook));
			}
		}

		return workbook;
	}

	private CellStyle getCellStyleBorder(Workbook workbook) {

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);

		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		return cellStyle;
	}

	private CellStyle getCellStyleLeft(Workbook workbook) {

		CellStyle style = getCellStyleBorder(workbook);

		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		return style;
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

			if (CollUtil.isEmpty(exportDangers)) {
				exportDangers.add(new DangerReivewExportUrbanVillageDangerDto());
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

					// List<String> locations = dangerList.stream().map((d) ->
					// d.getReportLocation())
					// .filter((d) ->
					// StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());

					String location = DangerLocationBuilder.buildString(dangerList);

					exportDanger.setLevel(oud.getLevel());
					exportDanger.setNo(String.valueOf(dangerIndex++));
					exportDanger.setSuggestions(oud.getSuggestions());
					exportDanger.setDescription(StrUtil.format("{} {}", location, oud.getDescription()));

					// 已整改隐患区域
					// List<String> finishLocations = dangerList.stream()
					// .filter((d) -> "2".equalsIgnoreCase(d.getStatus())).map((d) ->
					// d.getReportLocation())
					// .filter((d) ->
					// StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());
					// exportDanger.setFinishLocation(String.join("、", finishLocations));

					List<OwnerUnitDanger> finishDanger = dangerList.stream()
							.filter((d) -> "2".equalsIgnoreCase(d.getStatus())).collect(Collectors.toList());

					String finishLocation = DangerLocationBuilder.buildString(finishDanger);
					exportDanger.setFinishLocation(finishLocation);

					// 须整改隐患区域
					// List<String> unFinishLocations = dangerList.stream()
					// .filter((d) -> !"2".equalsIgnoreCase(d.getStatus())).map((d) ->
					// d.getReportLocation())
					// .filter((d) ->
					// StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());
					// exportDanger.setUnFinishLocation(String.join("、", unFinishLocations));

					List<OwnerUnitDanger> unFinishDangers = dangerList.stream()
							.filter((d) -> !"2".equalsIgnoreCase(d.getStatus())).collect(Collectors.toList());
					String unFinishLocation = DangerLocationBuilder.buildString(unFinishDangers);
					exportDanger.setUnFinishLocation(unFinishLocation);

					// 复查区域
					// List<String> reivewLocations = dangerList.stream()
					// .filter((d) -> "1".equalsIgnoreCase(d.getStatus())).map((d) ->
					// d.getReportLocation())
					// .filter((d) ->
					// StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());
					// exportDanger.setReviewLocation(String.join("、", reivewLocations));

					List<OwnerUnitDanger> reivewDangers = dangerList.stream()
							.filter((d) -> "1".equalsIgnoreCase(d.getStatus())).collect(Collectors.toList());
					String reivewLocation = DangerLocationBuilder.buildString(reivewDangers);
					exportDanger.setReviewLocation(reivewLocation);

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
