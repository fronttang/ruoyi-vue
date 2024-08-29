package com.ruoyi.electrical.danger.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.electrical.danger.controller.DangerExcelExportStylerImpl;
import com.ruoyi.electrical.dto.DangerExportIndustrialDto;
import com.ruoyi.electrical.dto.DangerExportQueryDto;
import com.ruoyi.electrical.dto.DangerExportRentalHouseDto;
import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerFormDataExportDto;
import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerFormExportDto;
import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerInfoExportDto;
import com.ruoyi.electrical.dto.DangerExportSmallDto;
import com.ruoyi.electrical.dto.IDangerExportDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.report.dto.high.HighDangerInfo;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class OwnerUnitDangerExportHighService {

	@Autowired
	private IOwnerUnitDangerExportService dangerExportService;

	@Autowired
	private ISysDictTypeService dictTypeService;

	private final Map<String, String> LEVEL_MAP = new HashMap<String, String>();

	public List<Map<String, Object>> exportDanger(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportQueryDto> exportData = new ArrayList<DangerExportQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportByUnitId(data.getIds()));
		} else {
			exportData.addAll(dangerExportService.exportByQuery(data));
		}

		List<SysDictData> levels = dictTypeService.selectDictDataByType("hazard_level_high");
		if (CollUtil.isNotEmpty(levels)) {
			for (SysDictData level : levels) {
				LEVEL_MAP.put(level.getDictValue(), level.getDictLabel());
			}
		}

		// 出租屋
		List<DangerExportRentalHouseDto> rentalHouse = exportData.stream()
				.filter((d) -> "1".equalsIgnoreCase(d.getHighRiskType())).map((d) -> {
					return buildRentalHouseExportData(d, DangerExportRentalHouseDto.class);
				}).collect(Collectors.toList());

		// 三小场所
		List<DangerExportSmallDto> small = exportData.stream().filter((d) -> "2".equalsIgnoreCase(d.getHighRiskType()))
				.map((d) -> {
					return buildRentalHouseExportData(d, DangerExportSmallDto.class);
				}).collect(Collectors.toList());

		// 住宅小区
		List<DangerExportSmallDto> residential = exportData.stream()
				.filter((d) -> "3".equalsIgnoreCase(d.getHighRiskType())).map((d) -> {
					return buildRentalHouseExportData(d, DangerExportSmallDto.class);
				}).collect(Collectors.toList());

		// 工业企业
		List<DangerExportIndustrialDto> industrial = exportData.stream()
				.filter((d) -> "4".equalsIgnoreCase(d.getHighRiskType())).map((d) -> {
					return buildRentalHouseExportData(d, DangerExportIndustrialDto.class);
				}).collect(Collectors.toList());

		// 公共场所
		List<DangerExportIndustrialDto> publicPlaces = exportData.stream()
				.filter((d) -> "5".equalsIgnoreCase(d.getHighRiskType())).map((d) -> {
					return buildRentalHouseExportData(d, DangerExportIndustrialDto.class);
				}).collect(Collectors.toList());

		// 大型综合体
		List<DangerExportIndustrialDto> complex = exportData.stream()
				.filter((d) -> "6".equalsIgnoreCase(d.getHighRiskType())).map((d) -> {
					return buildRentalHouseExportData(d, DangerExportIndustrialDto.class);
				}).collect(Collectors.toList());

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (CollUtil.isNotEmpty(rentalHouse)) {
			ExportParams exportParams = new ExportParams();
			// rentalHouseParams.setCreateHeadRows(false);
			exportParams.setSheetName("出租屋");
			exportParams.setTitle("出租屋火灾隐患排查表");
			exportParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> rentalHouseMap = new HashMap<String, Object>();
			rentalHouseMap.put("title", exportParams);
			rentalHouseMap.put("data", rentalHouse);
			rentalHouseMap.put("entity", DangerExportRentalHouseDto.class);
			list.add(rentalHouseMap);
		}

		if (CollUtil.isNotEmpty(small)) {
			ExportParams exportParams = new ExportParams();
			exportParams.setSheetName("三小场所");
			exportParams.setTitle("三小场所火灾隐患排查表");
			exportParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> smallMap = new HashMap<String, Object>();
			smallMap.put("title", exportParams);
			smallMap.put("data", small);
			smallMap.put("entity", DangerExportSmallDto.class);
			list.add(smallMap);
		}

		if (CollUtil.isNotEmpty(residential)) {
			ExportParams exportParams = new ExportParams();
			exportParams.setSheetName("住宅小区");
			exportParams.setTitle("住宅小区火灾隐患排查表");
			exportParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> residentialMap = new HashMap<String, Object>();
			residentialMap.put("title", exportParams);
			residentialMap.put("data", residential);
			residentialMap.put("entity", DangerExportSmallDto.class);
			list.add(residentialMap);
		}

		if (CollUtil.isNotEmpty(industrial)) {
			ExportParams exportParams = new ExportParams();
			exportParams.setSheetName("工业企业");
			exportParams.setTitle("工业企业火灾隐患排查表");
			exportParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> industrialMap = new HashMap<String, Object>();
			industrialMap.put("title", exportParams);
			industrialMap.put("data", industrial);
			industrialMap.put("entity", DangerExportIndustrialDto.class);
			list.add(industrialMap);
		}

		if (CollUtil.isNotEmpty(publicPlaces)) {
			ExportParams exportParams = new ExportParams();
			exportParams.setSheetName("公共场所");
			exportParams.setTitle("公共场所火灾隐患排查表");
			exportParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> publicPlacesMap = new HashMap<String, Object>();
			publicPlacesMap.put("title", exportParams);
			publicPlacesMap.put("data", publicPlaces);
			publicPlacesMap.put("entity", DangerExportIndustrialDto.class);
			list.add(publicPlacesMap);
		}

		if (CollUtil.isNotEmpty(complex)) {
			ExportParams exportParams = new ExportParams();
			exportParams.setSheetName("大型综合体");
			exportParams.setTitle("大型综合体火灾隐患排查表");
			exportParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> complexMap = new HashMap<String, Object>();
			complexMap.put("title", exportParams);
			complexMap.put("data", complex);
			complexMap.put("entity", DangerExportIndustrialDto.class);
			list.add(complexMap);
		}
		return list;
	}

	private <T extends IDangerExportDto> T buildRentalHouseExportData(DangerExportQueryDto data, Class<T> clazz) {
		T dto;
		try {
			dto = clazz.newInstance();
			BeanUtils.copyProperties(data, dto);
			if (StrUtil.isNotBlank(data.getBusinessLicense())) {
				dto.setBusinessLicense(readFileByte(data.getBusinessLicense()));
			}

			if ("1".equalsIgnoreCase(data.getInitialStatus())) {
				// 无法检测
				dto.setOpenStatus(StrUtil.format("无法检测({})", data.getIsTestReason()));
			}

		} catch (Exception e) {
			return null;
		}

		List<HighDangerInfo> dangers = data.getDangers();

		Map<Long, OwnerUnitDangerFormExportDto> formExportMap = new HashMap<Long, OwnerUnitDangerFormExportDto>();

		BigDecimal totalScore = BigDecimal.ZERO;
		if (CollUtil.isNotEmpty(dangers)) {

			// ComputeHighScoreService compute = new ComputeHighScoreService();
			// dto.setTotalScore(compute.compute(dangers));

			// 检测表最高扣分MAP
			Map<Long, BigDecimal> formMaxScoreMap = dangers.stream().filter((d) -> Objects.nonNull(d.getFormId()))
					.collect(Collectors.groupingBy(HighDangerInfo::getFormId, Collectors.toList())).entrySet().stream()
					.collect(Collectors.toMap((d) -> d.getKey(), (d) -> CollUtil.isNotEmpty(d.getValue())
							? Objects.nonNull(d.getValue().get(0).getMaxScore())
									? new BigDecimal(d.getValue().get(0).getMaxScore())
									: BigDecimal.ZERO
							: BigDecimal.ZERO));

			Map<Long, List<HighDangerInfo>> formMap = dangers.stream().filter((d) -> Objects.nonNull(d.getFormId()))
					.collect(Collectors.groupingBy(HighDangerInfo::getFormId, Collectors.toList()));

			Set<Entry<Long, List<HighDangerInfo>>> entrySet = formMap.entrySet();
			Iterator<Entry<Long, List<HighDangerInfo>>> iterator = entrySet.iterator();

			while (iterator.hasNext()) {

				Entry<Long, List<HighDangerInfo>> next = iterator.next();

				Long formId = next.getKey();
				List<HighDangerInfo> formDatas = next.getValue();

				OwnerUnitDangerFormExportDto formExport = formExportMap.get(formId);
				if (formExport == null) {
					formExport = new OwnerUnitDangerFormExportDto();
					formExportMap.put(formId, formExport);
				}

				BigDecimal formScore = computeFormDataScore(formDatas, formExport);

				BigDecimal formMaxScore = formMaxScoreMap.get(formId);

				if (formMaxScore.compareTo(BigDecimal.ZERO) > 0 && formScore.compareTo(formMaxScore) >= 0) {
					formExport.setMaxScore(formMaxScore.setScale(0, RoundingMode.HALF_UP).toPlainString());
					totalScore = totalScore.add(formMaxScore);
				} else {
					formExport.setMaxScore(formScore.setScale(0, RoundingMode.HALF_UP).toPlainString());
					totalScore = totalScore.add(formScore);
				}
			}
			totalScore = new BigDecimal(100).subtract(totalScore);
			if (totalScore.compareTo(BigDecimal.ZERO) <= 0) {
				totalScore = BigDecimal.ZERO;
			}
		}
		totalScore.setScale(0, RoundingMode.HALF_UP);

		dto.setTotalScore(totalScore.toPlainString());
		dto.setForms(new ArrayList<OwnerUnitDangerFormExportDto>(formExportMap.values()));
		return dto;
	}

	private BigDecimal computeFormDataScore(List<HighDangerInfo> formDatas, OwnerUnitDangerFormExportDto formExport) {
		// 检测项最高扣分数
		Map<Long, BigDecimal> formDataMaxScoreMap = formDatas.stream().filter((d) -> Objects.nonNull(d.getFormDataId()))
				.collect(Collectors.groupingBy(HighDangerInfo::getFormDataId, Collectors.toList())).entrySet().stream()
				.collect(Collectors.toMap((d) -> d.getKey(), (d) -> CollUtil.isNotEmpty(d.getValue())
						? Objects.nonNull(d.getValue().get(0).getDataMaxScore())
								? new BigDecimal(d.getValue().get(0).getDataMaxScore())
								: BigDecimal.ZERO
						: BigDecimal.ZERO));

		Map<Long, List<HighDangerInfo>> formDataMap = formDatas.stream()
				.filter((d) -> Objects.nonNull(d.getFormDataId()))
				.collect(Collectors.groupingBy(HighDangerInfo::getFormDataId, Collectors.toList()));

		Set<Entry<Long, List<HighDangerInfo>>> entrySet = formDataMap.entrySet();
		Iterator<Entry<Long, List<HighDangerInfo>>> iterator = entrySet.iterator();

		BigDecimal formDataTotal = BigDecimal.ZERO;

		while (iterator.hasNext()) {
			Entry<Long, List<HighDangerInfo>> next = iterator.next();

			Long formDataId = next.getKey();
			List<HighDangerInfo> dangers = next.getValue();

			BigDecimal dangerScore = computeDangerScore(dangers, formExport);
			BigDecimal formDataMaxScore = formDataMaxScoreMap.get(formDataId);

			if (formDataMaxScore.compareTo(BigDecimal.ZERO) > 0 && dangerScore.compareTo(formDataMaxScore) >= 0) {
				formDataTotal = formDataTotal.add(formDataMaxScore);
			} else {
				formDataTotal = formDataTotal.add(dangerScore);
			}
		}
		return formDataTotal;
	}

	private BigDecimal computeDangerScore(List<HighDangerInfo> dangers, OwnerUnitDangerFormExportDto formExport) {
		BigDecimal total = BigDecimal.ZERO;

		Map<String, List<HighDangerInfo>> accDangerMap = dangers.stream()
				.filter((d) -> Objects.nonNull(d.getAccMethod()))
				.collect(Collectors.groupingBy(HighDangerInfo::getAccMethod, Collectors.toList()));

		if (CollUtil.isNotEmpty(accDangerMap)) {

			Set<Entry<String, List<HighDangerInfo>>> entrySet = accDangerMap.entrySet();
			Iterator<Entry<String, List<HighDangerInfo>>> iterator = entrySet.iterator();

			List<OwnerUnitDangerFormDataExportDto> formDatas = formExport.getFormDatas();
			if (formDatas == null) {
				formDatas = new ArrayList<OwnerUnitDangerFormDataExportDto>();
				formExport.setFormDatas(formDatas);
			}

			while (iterator.hasNext()) {
				Entry<String, List<HighDangerInfo>> next = iterator.next();
				String accMethod = next.getKey();
				List<HighDangerInfo> accDangers = next.getValue();

				if (CollUtil.isNotEmpty(accDangers)) {

					// 按项累积
					if ("1".equalsIgnoreCase(accMethod)) {

						BigDecimal accTotal = computeAccMethod1(accDangers, formDatas);

						if (accTotal != null) {
							total = total.add(accTotal);
						}
					} else if ("2".equalsIgnoreCase(accMethod)) {

						for (HighDangerInfo danger : accDangers) {
							OwnerUnitDangerFormDataExportDto formDataExport = new OwnerUnitDangerFormDataExportDto();

							if (danger.getScore() != null) {
								BigDecimal score = new BigDecimal(dangers.get(0).getScore());
								formDataExport.setScore(score.setScale(0, RoundingMode.HALF_UP).toPlainString());
							}
							formDataExport.setDangers(Arrays.asList(buildDangerInfoExport(danger)));
							formDatas.add(formDataExport);
						}
						// 按个数累计
						Optional<BigDecimal> reduce = accDangers.stream().filter((d) -> Objects.nonNull(d.getScore()))
								.map((d) -> {
									return new BigDecimal(d.getScore());
								}).reduce(BigDecimal::add);

						if (reduce.isPresent()) {
							total = total.add(reduce.get());
						}
					}
				}
			}
		}
		return total;
	}

	/**
	 * 按项累积计算
	 * 
	 * @param accDangers
	 * @return
	 */
	private BigDecimal computeAccMethod1(List<HighDangerInfo> accDangers,
			List<OwnerUnitDangerFormDataExportDto> formDatas) {
		BigDecimal total = BigDecimal.ZERO;

		if (CollUtil.isNotEmpty(accDangers)) {

			Map<Long, List<HighDangerInfo>> accDangerMap = accDangers.stream()
					.filter((d) -> Objects.nonNull(d.getDangerId()))
					.collect(Collectors.groupingBy(HighDangerInfo::getDangerId, Collectors.toList()));
			if (CollUtil.isNotEmpty(accDangerMap)) {

				Set<Entry<Long, List<HighDangerInfo>>> entrySet = accDangerMap.entrySet();
				Iterator<Entry<Long, List<HighDangerInfo>>> iterator = entrySet.iterator();

				Map<Long, OwnerUnitDangerFormDataExportDto> formDataExportMap = new HashMap<Long, OwnerUnitDangerFormDataExportDto>();

				while (iterator.hasNext()) {
					Entry<Long, List<HighDangerInfo>> next = iterator.next();

					Long dangerId = next.getKey();
					List<HighDangerInfo> dangers = next.getValue();

					OwnerUnitDangerFormDataExportDto formData = formDataExportMap.get(dangerId);
					if (formData == null) {
						formData = new OwnerUnitDangerFormDataExportDto();
						formDataExportMap.put(dangerId, formData);
						formDatas.add(formData);
					}

					if (CollUtil.isNotEmpty(dangers)) {

						List<OwnerUnitDangerInfoExportDto> dangerExports = formData.getDangers();
						if (dangerExports == null) {
							dangerExports = new ArrayList<OwnerUnitDangerInfoExportDto>();
							formData.setDangers(dangerExports);
						}
						for (HighDangerInfo danger : dangers) {
							OwnerUnitDangerInfoExportDto infoExport = buildDangerInfoExport(danger);
							dangerExports.add(infoExport);
						}
					}
					if (CollUtil.isNotEmpty(dangers) && Objects.nonNull(dangers.get(0).getScore())) {

						BigDecimal score = new BigDecimal(dangers.get(0).getScore());

						formData.setScore(score.setScale(0, RoundingMode.HALF_UP).toPlainString());
						total = total.add(score);
					}
				}
			}
		}
		return total;
	}

	private OwnerUnitDangerInfoExportDto buildDangerInfoExport(HighDangerInfo danger) {
		OwnerUnitDangerInfoExportDto infoExport = new OwnerUnitDangerInfoExportDto();
		BeanUtils.copyProperties(danger, infoExport);

		infoExport.setLevel(LEVEL_MAP.get(danger.getLevel()));

		if ("2".equalsIgnoreCase(danger.getStatus())) {
			infoExport.setRectificationStatus("是");
		} else {
			infoExport.setRectificationStatus("否");
		}

		String dangerPic = danger.getDangerPic();
		if (StrUtil.isNotBlank(dangerPic)) {
			String[] dangerPicArr = dangerPic.split(",");
			if (dangerPicArr != null) {
				infoExport.setDangerPic1(readFileByte(dangerPicArr[0]));
				if (dangerPicArr.length > 1) {
					infoExport.setDangerPic2(readFileByte(dangerPicArr[1]));
				}
			}
		}

		if (StrUtil.isNotBlank(danger.getOverallPic())) {
			infoExport.setOverallPic1(readFileByte(danger.getOverallPic()));
		}

		String rectificationPic = danger.getRectificationPic();
		if (StrUtil.isNotBlank(rectificationPic)) {
			String[] rectificationPicArr = rectificationPic.split(",");
			if (rectificationPicArr != null) {
				infoExport.setRectificationPic1(readFileByte(rectificationPicArr[0]));
				if (rectificationPicArr.length > 1) {
					infoExport.setRectificationPic1(readFileByte(rectificationPicArr[1]));
				}
			}
		}
		return infoExport;
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
