package com.ruoyi.electrical.danger.service;

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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.dto.DangerExportStationDto;
import com.ruoyi.electrical.dto.DangerExportStationDto.DangerExportStationDangerDto;
import com.ruoyi.electrical.dto.DangerExportStationDto.StationPileInfo;
import com.ruoyi.electrical.dto.DangerExportStationQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.project.service.IChargingPileService;
import com.ruoyi.electrical.report.dto.station.ChargingPileInfo;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
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

	private final Map<String, String> LEVEL_MAP = new HashMap<String, String>();
	private final Map<String, String> DETECT_MODULE = new HashMap<String, String>();

	public List<DangerExportStationDto> exportDanger(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportStationQueryDto> exportData = new ArrayList<DangerExportStationQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportStationByUnitId(data.getIds()));
		} else {
			exportData.addAll(dangerExportService.exportStationByQuery(data));
		}

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

		List<DangerExportStationDto> datas = buildExportStationDto(exportData);

		// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// exportParams.setStyle(DangerExcelExportStylerImpl.class);

//		Map<String, Object> stationMap = new HashMap<String, Object>();
//		stationMap.put("title", exportParams);
//		stationMap.put("data", datas);
//		stationMap.put("entity", DangerExportStationDto.class);
//		list.add(stationMap);

		return datas;
	}

	private List<DangerExportStationDto> buildExportStationDto(List<DangerExportStationQueryDto> exportData) {

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

			List<ChargingPileInfo> stationPiles = chargingPileService.selectStationPileList(data.getId());
			if (CollUtil.isNotEmpty(stationPiles)) {
				StationPileInfo pileInfo = new StationPileInfo();

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
				export.setPileInfo(Arrays.asList(pileInfo));
			}

			List<DangerExportStationDangerDto> exportDangers = new LinkedList<DangerExportStationDangerDto>();
			buildExportDanger(exportDangers, dangers, data);
			export.setDangers(exportDangers);
			result.add(export);
		}
		return result;
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

							danger.setDescription(StrUtil.format("{}、{}{}", dangerIndex++, String.join(",", locations),
									ownerUnitDanger.getDescription()));
							danger.setLevel(LEVEL_MAP.get(ownerUnitDanger.getLevel()));
							danger.setSuggestions(ownerUnitDanger.getSuggestions());
							danger.setStatus(ownerUnitDanger.getStatus());

							List<String> dangerPics = descDangers.stream()
									.filter((d) -> StrUtil.isNotBlank(d.getDangerPic())).map((d) -> d.getDangerPic())
									.flatMap(str -> Arrays.stream(str.split(","))).collect(Collectors.toList());
							if (CollUtil.isNotEmpty(dangerPics)) {
								danger.setDangerPicture(readFileByte(dangerPics.get(0)));
							}

							List<String> rectificationPics = descDangers.stream()
									.filter((d) -> StrUtil.isNotBlank(d.getRectificationPic()))
									.map((d) -> d.getRectificationPic()).flatMap(str -> Arrays.stream(str.split(",")))
									.collect(Collectors.toList());
							if (CollUtil.isNotEmpty(rectificationPics)) {
								danger.setRectificationPicture(readFileByte(rectificationPics.get(0)));
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
