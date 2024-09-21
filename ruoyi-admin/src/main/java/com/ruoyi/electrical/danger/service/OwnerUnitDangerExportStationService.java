package com.ruoyi.electrical.danger.service;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.dto.DangerExportStationDto;
import com.ruoyi.electrical.dto.DangerExportStationDto.DangerExportStationDangerDto;
import com.ruoyi.electrical.dto.DangerExportStationDto.StationPileInfo;
import com.ruoyi.electrical.dto.DangerExportStationQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IChargingPileService;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.dto.station.ChargingPileInfo;
import com.ruoyi.electrical.report.dto.station.StationFormData;
import com.ruoyi.electrical.template.mapper.IntuitiveDetectDataMapper;
import com.ruoyi.electrical.util.PicUtils;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class OwnerUnitDangerExportStationService {

	@Autowired
	private IOwnerUnitDangerExportService dangerExportService;

	@Autowired
	private IChargingPileService chargingPileService;

	@Autowired
	private ISysDictTypeService dictTypeService;

	@Autowired
	private IntuitiveDetectDataMapper detectDataMapper;

	@Autowired
	private IProjectService projectService;

	private final Map<String, String> LEVEL_MAP = new HashMap<String, String>();
	private final Map<String, String> DETECT_MODULE = new HashMap<String, String>();

	public Workbook exportDanger(OwnerUnitDangerGroupDetailDto data, boolean isAllRounds) {

		List<DangerExportStationQueryDto> exportData = new ArrayList<DangerExportStationQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportStationByUnitId(data.getIds(), isAllRounds));
		} else {
			exportData.addAll(dangerExportService.exportStationByQuery(data, isAllRounds));
		}

		return exportDanger(exportData, isAllRounds);
	}

	private Workbook exportDanger(List<DangerExportStationQueryDto> exportData, boolean isAllRounds) {
		List<SysDictData> levels = dictTypeService.selectDictDataByType("hazard_level_charging_station");
		if (CollUtil.isNotEmpty(levels)) {
			for (SysDictData level : levels) {
				LEVEL_MAP.put(level.getDictValue(), level.getDictLabel());
			}
		}
		List<SysDictData> detectModuleDict = dictTypeService.selectDictDataByType("detect_module");
		if (CollUtil.isNotEmpty(detectModuleDict)) {
			for (SysDictData module : detectModuleDict) {
				DETECT_MODULE.put(module.getDictValue(), module.getDictLabel());
			}
		}

		List<DangerExportStationDto> exportDatas = buildExportStationDto(exportData, isAllRounds);

		LinkedList<DangerExportStationDto> exportDataList = new LinkedList<DangerExportStationDto>(exportDatas);

		ExportParams exportParams = new ExportParams();
		// rentalHouseParams.setCreateHeadRows(false);
		exportParams.setSheetName("隐患台账");
		exportParams.setTitle("充电站隐患台账");
		exportParams.setStyle(StationDangerExcelExportStylerImpl.class);
		exportParams.setDataHandler(new ExcelDataHandlerDefaultImpl<Object>() {
			@Override
			public Object exportHandler(Object obj, String name, Object value) {
				String stringValue = String.valueOf(value);
				if (StrUtil.isBlank(stringValue)) {
					stringValue = "/";
				}
				return super.exportHandler(obj, name, stringValue);
			}

			@Override
			public String[] getNeedHandlerFields() {

				Map<String, PropertyDescriptor> propertyDescriptorMap = BeanUtil
						.getPropertyDescriptorMap(DangerExportStationDto.class, false);
				List<String> fields = new ArrayList<String>();
				propertyDescriptorMap.forEach((name, pd) -> {
					if (String.class.equals(pd.getPropertyType()) || Long.class.equals(pd.getPropertyType())) {
						fields.add(name);
					}
				});

				Map<String, PropertyDescriptor> propertyDescriptorMap1 = BeanUtil
						.getPropertyDescriptorMap(StationPileInfo.class, false);
				propertyDescriptorMap1.forEach((name, pd) -> {
					if (String.class.equals(pd.getPropertyType()) || Long.class.equals(pd.getPropertyType())) {
						fields.add(name);
					}
				});

				Map<String, PropertyDescriptor> propertyDescriptorMap2 = BeanUtil
						.getPropertyDescriptorMap(DangerExportStationDangerDto.class, false);
				propertyDescriptorMap2.forEach((name, pd) -> {
					if (String.class.equals(pd.getPropertyType()) || Long.class.equals(pd.getPropertyType())) {
						fields.add(name);
					}
				});

				return fields.toArray(new String[fields.size()]);
			}
		});

		Workbook workbook = new XSSFWorkbook();
		StationDangerReportExcelExportService service = new StationDangerReportExcelExportService();
		service.createSheet(workbook, exportParams, DangerExportStationDto.class, exportDatas);

		StationDangerExcelExportStylerImpl styleImpl = new StationDangerExcelExportStylerImpl(workbook);

		Sheet sheet = workbook.getSheetAt(0);
		int index = 4;
		for (int i = 0; i < exportDataList.size(); i++) {
			DangerExportStationDto stationData = exportDataList.get(i);

			int size = 1;

			Row row1 = sheet.getRow(index);
			row1.getCell(23).setCellStyle(getLevelCellStyle(workbook, stationData));

			if (CollUtil.isNotEmpty(stationData.getDangers())) {

				List<DangerExportStationDangerDto> dangers = stationData.getDangers();

				size = dangers.size();

				row1.getCell(7).setCellStyle(styleImpl.stringSeptailStyle(workbook, true));
				row1.getCell(8).setCellStyle(styleImpl.stringSeptailStyle(workbook, true));
				row1.getCell(9).setCellStyle(styleImpl.stringSeptailStyle(workbook, true));
				row1.getCell(10).setCellStyle(styleImpl.stringSeptailStyle(workbook, true));

				PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 7, 7);
				PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 8, 8);
				PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 9, 9);
				PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 10, 10);

				// row1.getCell(7).setCellStyle(getDefaultCellStyle(workbook));
				// row1.getCell(8).setCellStyle(getDefaultCellStyle(workbook));
				// row1.getCell(9).setCellStyle(getDefaultCellStyle(workbook));
				// row1.getCell(10).setCellStyle(getDefaultCellStyle(workbook));

				// PoiMergeCellUtil.mergeCells(sheet, mergeMap, 7, 10);

				// PoiMergeCellUtil.mergeCells(sheet, 7, exportData.getDangers().size());
				// PoiMergeCellUtil.mergeCells(sheet, 8, exportData.getDangers().size());
				// PoiMergeCellUtil.mergeCells(sheet, 9, exportData.getDangers().size());
				// PoiMergeCellUtil.mergeCells(sheet, 10, exportData.getDangers().size());

				for (int j = 0; j < dangers.size(); j++) {
					DangerExportStationDangerDto danger = dangers.get(j);
					Row row = sheet.getRow(index + j);
					row.getCell(16).setCellStyle(getStationDangerStyle(workbook));
					row.getCell(17).setCellStyle(getStationDangerStyle(workbook));
					row.getCell(18).setCellStyle(getStationDangerStyle(workbook));
					row.getCell(19).setCellStyle(getStationDangerStyle(workbook));
					row.getCell(20).setCellStyle(getStationDangerStyle(workbook));
					row.getCell(21).setCellStyle(getStationDangerStyle(workbook));

					if ("true".equalsIgnoreCase(danger.getLevel())) {
						PoiMergeCellUtil.addMergedRegion(sheet, index + j, index + j, 16, 21);
						row.getCell(16).setCellStyle(getStationDangerTitleStyle(workbook));
					}
				}
			}
			index = index + size;
		}

		Row titleRow3 = sheet.getRow(3);
		for (int i = 0; i <= 24; i++) {
			if (i == 7 || i == 8 || i == 9 || i == 10) {
				continue;
			}
			titleRow3.createCell(i).setCellStyle(styleImpl.getTitleStyle((short) 0));
		}

		return workbook;
	}

	private CellStyle getLevelCellStyle(Workbook workbook, DangerExportStationDto station) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		if ("蓝色风险".equalsIgnoreCase(station.getLevel())) {
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
		} else if ("黄色风险".equalsIgnoreCase(station.getLevel())) {
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
		} else if ("橙色风险".equalsIgnoreCase(station.getLevel())) {
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
		} else if ("红色风险".equalsIgnoreCase(station.getLevel())) {
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		}
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle getStationDangerTitleStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);

		Font font = workbook.createFont();
		font.setBold(true);

		style.setFont(font);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		return style;
	}

	private CellStyle getStationDangerStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);

		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);

		style.setFont(font);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}

	private List<DangerExportStationDto> buildExportStationDto(List<DangerExportStationQueryDto> exportData,
			boolean isAllRounds) {

		Long index = 1L;
		List<DangerExportStationDto> result = new ArrayList<DangerExportStationDto>();
		for (DangerExportStationQueryDto data : exportData) {
			DangerExportStationDto export = new DangerExportStationDto();
			BeanUtils.copyProperties(data, export);
			export.setId(index++);
			List<OwnerUnitDanger> dangers = data.getDangers();

			// 排查检测日期
			if (Objects.nonNull(data.getInitialDate())) {
				export.setDetectDate(DateUtil.format(data.getInitialDate(), DatePattern.CHINESE_DATE_PATTERN));
			}

			Project project = projectService.selectProjectById(data.getProjectId());
			if (project != null) {
				List<StationFormData> formDatas = new ArrayList<StationFormData>();
				if (isAllRounds) {
					formDatas = detectDataMapper.selectStationDetectData(project.getTemplateId(), data.getId(),
							data.getRounds());
				} else {
					formDatas = detectDataMapper.selectStationDetectDataAllRounds(project.getTemplateId(),
							data.getId());
				}

				if (formDatas != null) {

					formDatas = formDatas.stream().filter((d) -> d.getDangers() > 0).collect(Collectors.toList());
					ComputeStationScoreService stationScoreService = new ComputeStationScoreService();
					BigDecimal compute = stationScoreService.compute(data.getDetectModule(), data.getStationType(),
							formDatas, dangers);
					compute = compute.setScale(2, RoundingMode.HALF_UP);

					export.setScore(compute.toPlainString());
					export.setLevel(getResult(compute.doubleValue()));
				}
			}

			List<DangerExportStationDangerDto> exportDangers = new LinkedList<DangerExportStationDangerDto>();
			buildExportDanger(exportDangers, dangers, data);
			if (CollUtil.isEmpty(exportDangers)) {
				exportDangers.add(new DangerExportStationDangerDto());
			}

			List<ChargingPileInfo> stationPiles = chargingPileService.selectStationPileList(data.getId());
			StationPileInfo pileInfo = new StationPileInfo();
			if (CollUtil.isNotEmpty(stationPiles)) {

				Optional<BigDecimal> quantity1 = stationPiles.stream()
						.filter((d) -> "非车载充电桩".equalsIgnoreCase(d.getType()))
						.filter((d) -> Objects.nonNull(d.getQuantity())).map((d) -> new BigDecimal(d.getQuantity()))
						.reduce(BigDecimal::add);

				if (quantity1.isPresent()) {
					BigDecimal quantity = quantity1.get();
					quantity = quantity.setScale(0, RoundingMode.HALF_UP);
					pileInfo.setQuantity1(quantity.longValue());
				} else {
					pileInfo.setQuantity1(0L);
				}

				Optional<BigDecimal> quantity2 = stationPiles.stream()
						.filter((d) -> "交流充电桩".equalsIgnoreCase(d.getType()))
						.filter((d) -> Objects.nonNull(d.getQuantity())).map((d) -> new BigDecimal(d.getQuantity()))
						.reduce(BigDecimal::add);

				if (quantity2.isPresent()) {
					BigDecimal quantity = quantity2.get();
					quantity = quantity.setScale(0, RoundingMode.HALF_UP);
					pileInfo.setQuantity2(quantity.longValue());
				} else {
					pileInfo.setQuantity2(0L);
				}

				pileInfo.setStationPileQuantity(pileInfo.getQuantity1() + pileInfo.getQuantity2());

				Optional<BigDecimal> power = stationPiles.stream().filter((d) -> Objects.nonNull(d.getPower()))
						.filter((d) -> NumberUtil.isNumber(d.getPower())).map((d) -> new BigDecimal(d.getPower()))
						.reduce(BigDecimal::add);

				if (power.isPresent()) {
					BigDecimal powersum = power.get();
					powersum = powersum.setScale(0, RoundingMode.HALF_UP);
					pileInfo.setStationPilePower(powersum.toPlainString());
				} else {
					pileInfo.setStationPilePower("");
				}
			}

			export.getPileInfo().add(pileInfo);

			for (int i = 0; i < exportDangers.size() - 1; i++) {
				export.getPileInfo().add(new StationPileInfo());
			}

			export.setDangers(exportDangers);
			result.add(export);
		}
		return result;
	}

	private String getResult(Double score) {

		if (score > 95) {
			return "蓝色风险";
		} else if (score <= 95 && score > 90) {
			return "黄色风险";
		} else if (score <= 90 && score > 80) {
			return "橙色风险";
		} else if (score <= 80) {
			return "红色风险";
		}
		return "";
	}

	private void buildExportDanger(List<DangerExportStationDangerDto> exportDangers, List<OwnerUnitDanger> dangers,
			DangerExportStationQueryDto data) {

		String detectModule = data.getDetectModule();
		if (StrUtil.isNotBlank(detectModule)) {

			List<String> detectModules = StrUtil.split(detectModule, ",");
			detectModules.sort(Comparator.naturalOrder());

			if (CollUtil.isNotEmpty(detectModules)) {
				// 按检测模块分组
				Map<String, List<OwnerUnitDanger>> detectModuleMap = new HashMap<String, List<OwnerUnitDanger>>();

				if (CollUtil.isNotEmpty(dangers)) {

					detectModuleMap = dangers.stream().filter((d) -> Objects.nonNull(d.getFormId()))
							.collect(Collectors.groupingBy(OwnerUnitDanger::getFormId, Collectors.toList())).entrySet()
							.stream().collect(Collectors.toMap((d) -> String.valueOf(d.getKey()), (d) -> d.getValue()));
				}

				for (String module : detectModules) {

					DangerExportStationDangerDto moduleDanger = new DangerExportStationDangerDto();
					moduleDanger.setDescription(StrUtil.format("{}风险点：", DETECT_MODULE.get(module)));
					moduleDanger.setLevel("true");
					exportDangers.add(moduleDanger);

					List<OwnerUnitDanger> moduleDangers = detectModuleMap.get(module);
					if (CollUtil.isNotEmpty(moduleDangers)) {

						// 按描述分组
						Map<String, List<OwnerUnitDanger>> descGroupDangerMap = moduleDangers.stream()
								.filter((d) -> StrUtil.isNotBlank(d.getDescription()))
								.collect(Collectors.groupingBy(OwnerUnitDanger::getDescription, Collectors.toList()));
						Set<Entry<String, List<OwnerUnitDanger>>> descEntrySet = descGroupDangerMap.entrySet();
						Iterator<Entry<String, List<OwnerUnitDanger>>> descIterator = descEntrySet.iterator();

						int dangerIndex = 1;
						while (descIterator.hasNext()) {
							Entry<String, List<OwnerUnitDanger>> descNext = descIterator.next();
							List<OwnerUnitDanger> descDangers = descNext.getValue();

							DangerExportStationDangerDto danger = new DangerExportStationDangerDto();
							OwnerUnitDanger ownerUnitDanger = descDangers.get(0);

							List<String> locations = descDangers.stream()
									.filter((d) -> StrUtil.isNotBlank(d.getLocation()))
									.map(OwnerUnitDanger::getLocation).distinct().collect(Collectors.toList());

							long count = descDangers.stream().filter(
									(d) -> "0".equalsIgnoreCase(d.getStatus()) || "1".equalsIgnoreCase(d.getStatus()))
									.count();

							if (count > 0) {
								danger.setStatus("0");
							} else {
								danger.setStatus("2");
							}

							danger.setDescription(StrUtil.format("{}、{}{}", dangerIndex++, String.join("、", locations),
									ownerUnitDanger.getDescription()));
							danger.setLevel(LEVEL_MAP.get(ownerUnitDanger.getLevel()));
							danger.setSuggestions(ownerUnitDanger.getSuggestions());

							List<String> dangerPics = descDangers.stream()
									.filter((d) -> StrUtil.isNotBlank(d.getDangerPic())).map((d) -> d.getDangerPic())
									.flatMap(str -> Arrays.stream(str.split(","))).collect(Collectors.toList());
							if (CollUtil.isNotEmpty(dangerPics)) {
								danger.setDangerPicture(PicUtils.readFileByte(dangerPics.get(0)));
							}

							List<String> rectificationPics = descDangers.stream()
									.filter((d) -> StrUtil.isNotBlank(d.getRectificationPic()))
									.map((d) -> d.getRectificationPic()).flatMap(str -> Arrays.stream(str.split(",")))
									.collect(Collectors.toList());
							if (CollUtil.isNotEmpty(rectificationPics)) {
								danger.setRectificationPicture(PicUtils.readFileByte(rectificationPics.get(0)));
							}

							exportDangers.add(danger);
						}

					} else {
						DangerExportStationDangerDto nonullDanger = new DangerExportStationDangerDto();
						nonullDanger.setDescription("无");
						nonullDanger.setLevel("/");
						nonullDanger.setSuggestions("无");
						exportDangers.add(nonullDanger);
					}
				}
			}
		}
	}
}
