package com.ruoyi.electrical.project.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.electrical.project.domain.AreaDict;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.domain.ProjectArea;
import com.ruoyi.electrical.project.dto.OwnerUnitImportChargingStationDto;
import com.ruoyi.electrical.project.dto.OwnerUnitImportHighDto;
import com.ruoyi.electrical.project.dto.OwnerUnitImportHighIndustrialDto;
import com.ruoyi.electrical.project.dto.OwnerUnitImportHighRentalHouseDto;
import com.ruoyi.electrical.project.dto.OwnerUnitImportHighResidentialDto;
import com.ruoyi.electrical.project.dto.OwnerUnitImportHighSmallDto;
import com.ruoyi.electrical.project.dto.OwnerUnitImportResultDto;
import com.ruoyi.electrical.project.dto.OwnerUnitImportUrbanVillageDto;
import com.ruoyi.electrical.project.service.IAreaDictService;
import com.ruoyi.electrical.project.service.IOwnerUnitImportService;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.project.service.IProjectAreaService;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OwnerUnitImportServiceImpl implements IOwnerUnitImportService {

	@Autowired
	private IProjectAreaService projectAreaService;

	@Autowired
	private IAreaDictService areaDictService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private ISysDictTypeService dictTypeService;
	
	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Override
	public AjaxResult importOwnerUnit(Long projectId, String highRiskType, MultipartFile file) throws Exception {

		Project project = projectService.selectProjectById(projectId);
		if (project == null) {
			return AjaxResult.error("项目不存在");
		}

		if ("3".equalsIgnoreCase(project.getType()) && StrUtil.isBlank(highRiskType)) {
			return AjaxResult.error("场所类型不正确");
		}

		// 项目区域字典
		AreaDict areaQuery = new AreaDict();
		areaQuery.setProjectId(projectId);
		List<AreaDict> areaDict = areaDictService.selectAreaDictList(areaQuery);

		Map<String, String> districtDict = new HashMap<String, String>();
		Map<String, String> streetDict = new HashMap<String, String>();
		Map<String, String> communityDict = new HashMap<String, String>();
		Map<String, String> hamletDict = new HashMap<String, String>();

		if (CollUtil.isNotEmpty(areaDict)) {
			districtDict.putAll(areaDict.stream().filter((item) -> "district".equalsIgnoreCase(item.getDictType()))
					.collect(Collectors.toMap(AreaDict::getDictLabel, AreaDict::getDictValue, (v1, v2) -> v2)));
			streetDict.putAll(areaDict.stream().filter((item) -> "street".equalsIgnoreCase(item.getDictType()))
					.collect(Collectors.toMap(AreaDict::getDictLabel, AreaDict::getDictValue, (v1, v2) -> v2)));
			communityDict.putAll(areaDict.stream().filter((item) -> "community".equalsIgnoreCase(item.getDictType()))
					.collect(Collectors.toMap(AreaDict::getDictLabel, AreaDict::getDictValue, (v1, v2) -> v2)));
			hamletDict.putAll(areaDict.stream().filter((item) -> "hamlet".equalsIgnoreCase(item.getDictType()))
					.collect(Collectors.toMap(AreaDict::getDictLabel, AreaDict::getDictValue, (v1, v2) -> v2)));
		}

		log.info("districtDict:{}", JSON.toJSONString(districtDict));
		log.info("streetDict:{}", JSON.toJSONString(streetDict));
		log.info("communityDict:{}", JSON.toJSONString(communityDict));
		log.info("hamletDict:{}", JSON.toJSONString(hamletDict));

		// 获取项目区域配置
		List<ProjectArea> projectAreaLists = projectAreaService.queryProjectAreaByProjectId(projectId);

		log.info("projectAreaLists:{}", JSON.toJSONString(projectAreaLists));

		// 工业园/出租屋
		if ("1".equalsIgnoreCase(project.getType()) || "2".equalsIgnoreCase(project.getType())) {

			return handleImportData(file, project, null, districtDict, streetDict, communityDict, hamletDict,
					projectAreaLists, OwnerUnitImportUrbanVillageDto.class);
		} else if ("3".equalsIgnoreCase(project.getType())) {
			// 高风险
			if ("1".equalsIgnoreCase(highRiskType)) {
				// 1 出租屋
				return handleImportData(file, project, highRiskType, districtDict, streetDict, communityDict,
						hamletDict, projectAreaLists, OwnerUnitImportHighRentalHouseDto.class);
			} else if ("2".equalsIgnoreCase(highRiskType)) {
				// 2 三小场所
				return handleImportData(file, project, highRiskType, districtDict, streetDict, communityDict,
						hamletDict, projectAreaLists, OwnerUnitImportHighSmallDto.class);
			} else if ("3".equalsIgnoreCase(highRiskType)) {
				// 3 住宅小区
				return handleImportData(file, project, highRiskType, districtDict, streetDict, communityDict,
						hamletDict, projectAreaLists, OwnerUnitImportHighResidentialDto.class);
			} else if ("4".equalsIgnoreCase(highRiskType) || "5".equalsIgnoreCase(highRiskType)
					|| "6".equalsIgnoreCase(highRiskType)) {
				// 4 工业企业 5 公共场所 6 大型综合体
				return handleImportData(file, project, highRiskType, districtDict, streetDict, communityDict,
						hamletDict, projectAreaLists, OwnerUnitImportHighIndustrialDto.class);
			}
		} else if ("4".equalsIgnoreCase(project.getType())) {
			return handleImportData(file, project, null, districtDict, streetDict, communityDict, hamletDict,
					projectAreaLists, OwnerUnitImportChargingStationDto.class);
		}
		return AjaxResult.error();
	}

	private <T extends OwnerUnitImportResultDto> AjaxResult handleImportData(MultipartFile file, Project project,
			String highRiskType, Map<String, String> districtDict, Map<String, String> streetDict,
			Map<String, String> communityDict, Map<String, String> hamletDict, List<ProjectArea> projectAreaLists,
			Class<T> clazz) {
		List<T> importData = readImportData(file, clazz);

		if (CollUtil.isNotEmpty(importData)) {
			List<SysDictData> chargingStationType = dictTypeService.selectDictDataByType("charging_station_type");
			Map<String, String> chargingStationTypeMap = new HashMap<String, String>();

			if (CollUtil.isNotEmpty(chargingStationType)) {
				chargingStationTypeMap.putAll(chargingStationType.stream().collect(
						Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue, (v1, v2) -> v2)));
			}

			List<SysDictData> propertyType = dictTypeService.selectDictDataByType("property_type");
			Map<String, String> propertyTypeMap = new HashMap<String, String>();

			if (CollUtil.isNotEmpty(propertyType)) {
				propertyTypeMap.putAll(propertyType.stream().collect(
						Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue, (v1, v2) -> v2)));
			}
			
			List<SysDictData> nature = dictTypeService.selectDictDataByType("building_nature");
			Map<String, String> natureMap = new HashMap<String, String>();

			if (CollUtil.isNotEmpty(nature)) {
				natureMap.putAll(nature.stream().collect(
						Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue, (v1, v2) -> v2)));
			}
			
			List<SysDictData> detectContent = dictTypeService.selectDictDataByType("detect_content");
			Map<String, String> detectContentMap = new HashMap<String, String>();

			if (CollUtil.isNotEmpty(detectContent)) {
				detectContentMap.putAll(detectContent.stream().collect(
						Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue, (v1, v2) -> v2)));
			}

			for (T data : importData) {
				try {
					log.info("importData:{}", JSON.toJSONString(data));
					ProjectArea projectArea = null;
					if ("1".equalsIgnoreCase(project.getType())) {
						projectArea = checkUrbanVillageData((OwnerUnitImportUrbanVillageDto) data, districtDict,
								streetDict, communityDict, hamletDict, projectAreaLists);
					} else if ("2".equalsIgnoreCase(project.getType())) {
						projectArea = checkIndustrialAreaData((OwnerUnitImportUrbanVillageDto) data, districtDict,
								streetDict, projectAreaLists);
					} else if ("3".equalsIgnoreCase(project.getType())) {
						projectArea = checkHighData((OwnerUnitImportHighDto) data, districtDict, streetDict,
								communityDict, projectAreaLists);
					} else if ("4".equalsIgnoreCase(project.getType())) {
						projectArea = checkChargingStationData((OwnerUnitImportChargingStationDto) data, districtDict,
								streetDict, projectAreaLists, chargingStationTypeMap, propertyTypeMap);
					}

					log.info("projectArea:{}", JSON.toJSONString(projectArea));

					if (projectArea == null) {
						continue;
					}

					OwnerUnit ownerUnit = new OwnerUnit();
					BeanUtils.copyProperties(data, ownerUnit);
					ownerUnit.setHighRiskType(highRiskType);
					ownerUnit.setProjectId(project.getId());
					ownerUnit.setType(project.getType());
					ownerUnit.setDetectId(project.getDetectId());
					ownerUnit.setProjectName(project.getName());
					ownerUnit.setDistrict(projectArea.getDistrict());
					ownerUnit.setStreet(projectArea.getStreet());
					ownerUnit.setCommunity(projectArea.getCommunity());
					ownerUnit.setHamlet(projectArea.getHamlet());
					ownerUnit.setArea(projectArea.getId());
					ownerUnit.setNature(natureMap.get(ownerUnit.getNature()));
					ownerUnit.setTestContent(buildDetectContent(ownerUnit.getTestContent(), detectContentMap));
					
					if ("4".equalsIgnoreCase(project.getType())) {
						buildChargingStationData(ownerUnit, chargingStationTypeMap, propertyTypeMap);
					}
					
					OwnerUnit exsit = ownerUnitService.checkOwnerUnitName(ownerUnit);
					
					if (Objects.nonNull(exsit)) {
						//data.setResult("名称重复");
						//continue;
						ownerUnit.setId(exsit.getId());
						ownerUnitService.updateOwnerUnit(ownerUnit);
					} else {
						ownerUnit.setCreateBy("admin");
						ownerUnit.setCreateTime(DateUtils.getNowDate());
						ownerUnit.setUpdateTime(DateUtils.getNowDate());
						ownerUnitService.insertOwnerUnit(ownerUnit);
					}
					
					ownerUnitReportService.selectOwnerUnitReportByUnitIdAndTypeAndCode(ownerUnit.getId(), "1", ownerUnit.getInitCode());
					ownerUnitReportService.selectOwnerUnitReportByUnitIdAndTypeAndCode(ownerUnit.getId(), "2", ownerUnit.getReviewCode());
					
					data.setResult("导入成功");
				} catch (Exception e) {
					data.setResult(StrUtil.format("导入失败：{}", e.getMessage()));
				}
			}
			try {
				ExportParams exportParams = new ExportParams();
				Workbook exportExcel = ExcelExportUtil.exportExcel(exportParams, clazz, importData);

				String fileUrl = writeToFile(exportExcel, file);
				return AjaxResult.success("操作成功", fileUrl);
			} catch (Exception e) {
				log.error("", e);
				return AjaxResult.error("数据导入成功, 生成导入结果出错。");
			}
		}
		return AjaxResult.error();
	}
	
	private String buildDetectContent(String detectContent, Map<String, String> detectContentMap) {
		if (StrUtil.isBlank(detectContent)) {
			return null;
		}
		
		return Stream.of(detectContent.split("、"))
	            .map(String::trim)
	            .map(detectContentMap::get)
	            .filter(StrUtil::isNotBlank)
	            .collect(Collectors.joining(","));
	} 

	private void buildChargingStationData(OwnerUnit ownerUnit, Map<String, String> chargingStationTypeMap,
			Map<String, String> propertyTypeMap) {

		ownerUnit.setStationType(chargingStationTypeMap.get(ownerUnit.getStationType()));
		String propertyType = propertyTypeMap.get(ownerUnit.getPropertyType());
		if (StrUtil.isNotBlank(propertyType)) {
			ownerUnit.setPropertyType(propertyType);
		} else {
			ownerUnit.setPropertyName(ownerUnit.getPropertyType());
			ownerUnit.setPropertyType("4");
		}
	}

	private ProjectArea checkUrbanVillageData(OwnerUnitImportUrbanVillageDto data, Map<String, String> districtDict,
			Map<String, String> streetDict, Map<String, String> communityDict, Map<String, String> hamletDict,
			List<ProjectArea> projectAreaLists) {
		String name = data.getName();
		if (StrUtil.isBlank(name)) {
			data.setResult("楼栋名称不能为空");
			return null;
		}
		String district = districtDict.get(data.getDistrict());
		String street = streetDict.get(data.getStreet());
		String community = communityDict.get(data.getCommunity());
		String hamlet = hamletDict.get(data.getHamlet());
		if (StrUtil.isBlank(data.getDistrict()) || StrUtil.isBlank(data.getStreet())
				|| StrUtil.isBlank(data.getCommunity()) || StrUtil.isBlank(data.getHamlet())) {
			data.setResult("区/街道/社区/村 不能为空");
			return null;
		}
		if (StrUtil.isBlank(district) || StrUtil.isBlank(street) || StrUtil.isBlank(community)
				|| StrUtil.isBlank(hamlet)) {
			data.setResult("区/街道/社区/村 项目中没有配置");
			return null;
		}

		ProjectArea projectArea = getProjectArea(projectAreaLists, district, street, community, hamlet);
		if (projectArea == null) {
			data.setResult("区/街道/社区/村 项目中未配置");
			return null;
		}
		return projectArea;
	}

	private ProjectArea checkIndustrialAreaData(OwnerUnitImportUrbanVillageDto data, Map<String, String> districtDict,
			Map<String, String> streetDict, List<ProjectArea> projectAreaLists) {
		String name = data.getName();
		if (StrUtil.isBlank(name)) {
			data.setResult("楼栋名称不能为空");
			return null;
		}
		String district = districtDict.get(data.getDistrict());
		String street = streetDict.get(data.getStreet());
		if (StrUtil.isBlank(data.getDistrict()) || StrUtil.isBlank(data.getStreet())) {
			data.setResult("区/街道 不能为空");
			return null;
		}
		if (StrUtil.isBlank(district) || StrUtil.isBlank(street)) {
			data.setResult("区/街道 项目中没有配置");
			return null;
		}

		ProjectArea projectArea = getProjectArea(projectAreaLists, district, street);
		if (projectArea == null) {
			data.setResult("区/街道 项目中未配置");
			return null;
		}
		return projectArea;
	}

	private ProjectArea checkHighData(OwnerUnitImportHighDto data, Map<String, String> districtDict,
			Map<String, String> streetDict, Map<String, String> communityDict, List<ProjectArea> projectAreaLists) {
		String name = data.getName();
		if (StrUtil.isBlank(name)) {
			data.setResult("场所名称不能为空");
			return null;
		}
		String district = districtDict.get(data.getDistrict());
		String street = streetDict.get(data.getStreet());
		String community = communityDict.get(data.getCommunity());

		log.info("district:{}", district);
		log.info("street:{}", street);
		log.info("community:{}", community);

		if (StrUtil.isBlank(data.getDistrict()) || StrUtil.isBlank(data.getStreet())
				|| StrUtil.isBlank(data.getCommunity())) {
			data.setResult("区/街道/社区 不能为空");
			return null;
		}
		if (StrUtil.isBlank(district) || StrUtil.isBlank(street) || StrUtil.isBlank(community)) {
			data.setResult("区/街道/社区 项目中没有配置");
			return null;
		}

		ProjectArea projectArea = getProjectArea(projectAreaLists, district, street, community);
		log.info("projectArea:{}", JSON.toJSONString(projectArea));
		if (projectArea == null) {
			data.setResult("区/街道/社区 项目中未配置");
			return null;
		}
		return projectArea;
	}

	private ProjectArea checkChargingStationData(OwnerUnitImportChargingStationDto data,
			Map<String, String> districtDict, Map<String, String> streetDict, List<ProjectArea> projectAreaLists,
			Map<String, String> chargingStationTypeMap, Map<String, String> propertyTypeMap) {
		String name = data.getName();
		if (StrUtil.isBlank(name)) {
			data.setResult("场所名称不能为空");
			return null;
		}
		String district = districtDict.get(data.getDistrict());
		String street = streetDict.get(data.getStreet());
		if (StrUtil.isBlank(data.getDistrict()) || StrUtil.isBlank(data.getStreet())) {
			data.setResult("区/街道 不能为空");
			return null;
		}
		if (StrUtil.isBlank(district) || StrUtil.isBlank(street)) {
			data.setResult("区/街道 项目中没有配置");
			return null;
		}

		ProjectArea projectArea = getProjectArea(projectAreaLists, district, street);
		if (projectArea == null) {
			data.setResult("区/街道 项目中未配置");
			return null;
		}

		if (chargingStationTypeMap.get(data.getStationType()) == null) {
			data.setResult("类型为空或不正确");
			return null;
		}

		return projectArea;
	}

	private <T extends OwnerUnitImportResultDto> List<T> readImportData(MultipartFile file, Class<T> clazz) {
		try {
			ImportParams params = new ImportParams();
			params.setHeadRows(1);

			@Cleanup
			InputStream inputStream = file.getInputStream();
			List<T> importData = ExcelImportUtil.importExcel(file.getInputStream(), clazz, params);
			return importData;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	private String writeToFile(Workbook exportExcel, MultipartFile file) {
		try {
			LocalDateTime now = LocalDateTime.now();
			String datePath = DateUtil.format(now, "yyyy/MM/dd");
			String uuid = IdUtil.getSnowflakeNextIdStr();
			String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
			String fileName = StrUtil.format("{}/{}/{}.{}", datePath, uuid, baseName,
					FileUploadUtils.getExtension(file));

			String baseDir = RuoYiConfig.getUploadPath();
			File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, fileName);
			exportExcel.write(new FileOutputStream(saveFile));

			return FileUploadUtils.getPathFileName(baseDir, fileName);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	private ProjectArea getProjectArea(List<ProjectArea> projectArea, String district, String street, String community,
			String hamlet) {
		try {
			if (CollUtil.isNotEmpty(projectArea)) {
				return projectArea.stream().filter((item) -> {
					return item.getDistrict().equalsIgnoreCase(district) && item.getStreet().equalsIgnoreCase(street)
							&& item.getCommunity().equalsIgnoreCase(community)
							&& item.getHamlet().equalsIgnoreCase(hamlet);
				}).findFirst().get();
			}
		} catch (Exception e) {
		}
		return null;
	}

	private ProjectArea getProjectArea(List<ProjectArea> projectArea, String district, String street) {
		try {
			if (CollUtil.isNotEmpty(projectArea)) {
				return projectArea.stream().filter((item) -> {
					return item.getDistrict().equalsIgnoreCase(district) && item.getStreet().equalsIgnoreCase(street);
				}).findFirst().get();
			}
		} catch (Exception e) {
		}
		return null;
	}

	private ProjectArea getProjectArea(List<ProjectArea> projectArea, String district, String street,
			String community) {
		try {
			if (CollUtil.isNotEmpty(projectArea)) {
				return projectArea.stream().filter((item) -> {
					return item.getDistrict().equalsIgnoreCase(district) && item.getStreet().equalsIgnoreCase(street)
							&& item.getCommunity().equalsIgnoreCase(community);
				}).findFirst().get();
			}
		} catch (Exception e) {
		}
		return null;
	}
}
