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
import com.ruoyi.electrical.dto.DangerExportUrbanVillageDto;
import com.ruoyi.electrical.dto.DangerExportUrbanVillageDto.DangerExportUrbanVillageDangerDto;
import com.ruoyi.electrical.dto.DangerExportUrbanVillageQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.util.PicUtils;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class OwnerUnitDangerExportUrbanVillageService {

	@Autowired
	private IOwnerUnitDangerExportService dangerExportService;

	public Workbook exportDanger(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportUrbanVillageQueryDto> exportData = new ArrayList<DangerExportUrbanVillageQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportUrbanVillageByUnitId(data.getIds()));
		} else {
			exportData.addAll(dangerExportService.exportUrbanVillageByQuery(data));
		}

		List<DangerExportUrbanVillageDto> datas = buildUrbanVillageDto(exportData);

		ExportParams exportParams = new ExportParams();
		// rentalHouseParams.setCreateHeadRows(false);
		exportParams.setSheetName("隐患台账");
		exportParams.setTitle("城中村出租屋用电安全隐患台账明细表");
		exportParams.setStyle(DangerExcelExportStylerImpl.class);

		Workbook workbook = new XSSFWorkbook();
		DangerReportExcelExportService service = new DangerReportExcelExportService();
		service.createSheet(workbook, exportParams, DangerExportUrbanVillageDto.class, datas);

		for (Sheet sheet : workbook) {
			for (int i = 2; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				row.getCell(10).setCellStyle(getCellStyleBorder(workbook));
				row.getCell(11).setCellStyle(getCellStyleBorder(workbook));
				row.getCell(12).setCellStyle(getCellStyleLeft(workbook));
				row.getCell(13).setCellStyle(getCellStyleLeft(workbook));

				row.getCell(37).setCellStyle(getCellStyleBorder(workbook));
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

	private List<DangerExportUrbanVillageDto> buildUrbanVillageDto(List<DangerExportUrbanVillageQueryDto> exportData) {

		Long index = 1L;
		List<DangerExportUrbanVillageDto> result = new ArrayList<DangerExportUrbanVillageDto>();
		for (DangerExportUrbanVillageQueryDto data : exportData) {
			DangerExportUrbanVillageDto export = new DangerExportUrbanVillageDto();
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

			Long detectDoorNumber = data.getAreas();
			// 入户检测房间数量
			export.setDetectDoorNumber(detectDoorNumber);
			// 检测率
			if (Objects.nonNull(detectDoorNumber) && detectDoorNumber > 0 && Objects.nonNull(data.getDoorNumber())
					&& data.getDoorNumber() > 0) {
				BigDecimal rate = new BigDecimal(detectDoorNumber).divide(new BigDecimal(data.getDoorNumber()), 8,
						BigDecimal.ROUND_HALF_UP);
				rate = new BigDecimal(100).multiply(rate);
				rate = rate.setScale(2, RoundingMode.HALF_UP);
				export.setHouseholdRate(rate.toPlainString() + "%");
			}

			// 隐患总数量
			long danger = dangers.stream().filter(d -> StrUtil.isNotBlank(d.getStatus()) && !"9".equals(d.getStatus())).count();
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

			// 公共区域隐患数量
			long dangerAreaType1 = dangers.stream().filter((d) -> "1".equalsIgnoreCase(d.getAreaType())).count();
			export.setDangerAreaType1(dangerAreaType1);

			// 户内隐患数量
			long dangerAreaType2 = dangers.stream().filter((d) -> "2".equalsIgnoreCase(d.getAreaType())).count();
			export.setDangerAreaType2(dangerAreaType2);

			// 楼栋检测工作照
			if (StrUtil.isNotBlank(data.getInspectionPic())) {
				List<String> pics = StrUtil.split(",", data.getInspectionPic());
				if (CollUtil.isNotEmpty(pics)) {
					export.setInspectionPicture(PicUtils.readFileByte(pics.get(0)));
				}
			}

			List<DangerExportUrbanVillageDangerDto> exportDangers = new ArrayList<DangerExportUrbanVillageDangerDto>();

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
				exportDangers.add(new DangerExportUrbanVillageDangerDto());
			}

			export.setDangers(exportDangers);
			result.add(export);
		}
		return result;
	}

	private void buildExportDanger(List<DangerExportUrbanVillageDangerDto> exportDangers,
			Map<String, List<OwnerUnitDanger>> formbDangerGroupMap) {
		if (CollUtil.isNotEmpty(formbDangerGroupMap)) {

			Set<Entry<String, List<OwnerUnitDanger>>> entrySet = formbDangerGroupMap.entrySet();
			Iterator<Entry<String, List<OwnerUnitDanger>>> iterator = entrySet.iterator();

			int dangerIndex = 1;

			while (iterator.hasNext()) {
				Entry<String, List<OwnerUnitDanger>> next = iterator.next();
				List<OwnerUnitDanger> dangerList = next.getValue();

				if (CollUtil.isNotEmpty(dangerList)) {
					DangerExportUrbanVillageDangerDto exportDanger = new DangerExportUrbanVillageDangerDto();
					OwnerUnitDanger oud = dangerList.get(0);

					// List<String> locations = dangerList.stream().map((d) ->
					// d.getReportLocation()).distinct()
					// .collect(Collectors.toList());

					String location = DangerLocationBuilder.buildString(dangerList);

					exportDanger.setLevel(oud.getLevel());
					exportDanger.setNo(String.valueOf(dangerIndex++));
					exportDanger.setSuggestions(oud.getSuggestions());
					exportDanger.setDescription(StrUtil.format("{} {}", location, oud.getDescription()));

					exportDangers.add(exportDanger);
				}
			}
		}
	}

}
