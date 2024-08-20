package com.ruoyi.electrical.danger.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.electrical.danger.service.ComputeHighScoreService;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerExportService;
import com.ruoyi.electrical.dto.DangerExportIndustrialDto;
import com.ruoyi.electrical.dto.DangerExportQueryDto;
import com.ruoyi.electrical.dto.DangerExportRentalHouseDto;
import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerDataExportDto;
import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerInfoExportDto;
import com.ruoyi.electrical.dto.DangerExportSmallDto;
import com.ruoyi.electrical.dto.IDangerExportDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.report.dto.high.HighDangerInfo;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

@RestController
@RequestMapping("/owner/unit/danger")
public class OwnerUnitDangerExportController extends BaseController {

	@Autowired
	private IOwnerUnitDangerExportService dangerExportService;

	private static final Map<String, String> LEVEL_MAP = new HashMap<String, String>();
	static {
		LEVEL_MAP.put("1", "低风险");
		LEVEL_MAP.put("2", "中风险");
		LEVEL_MAP.put("3", "高风险");
		LEVEL_MAP.put("4", "无等级");
	}

	@RequestMapping("/export")
	public void export(OwnerUnitDangerGroupDetailDto data, HttpServletResponse response) {

		List<DangerExportQueryDto> exportData = new ArrayList<DangerExportQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(dangerExportService.exportByUnitId(data.getIds()));
		} else {
			exportData.addAll(dangerExportService.exportByQuery(data));
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
			ExportParams rentalHouseParams = new ExportParams();
			rentalHouseParams.setSheetName("出租屋");
			rentalHouseParams.setTitle("出租屋火灾隐患排查表");
			Map<String, Object> rentalHouseMap = new HashMap<String, Object>();
			rentalHouseMap.put("title", rentalHouseParams);
			rentalHouseMap.put("data", rentalHouse);
			rentalHouseMap.put("entity", DangerExportRentalHouseDto.class);
			list.add(rentalHouseMap);
		}

		if (CollUtil.isNotEmpty(small)) {
			ExportParams smallParams = new ExportParams();
			smallParams.setSheetName("三小场所");
			smallParams.setTitle("三小场所火灾隐患排查表");
			Map<String, Object> smallMap = new HashMap<String, Object>();
			smallMap.put("title", smallParams);
			smallMap.put("data", small);
			smallMap.put("entity", DangerExportSmallDto.class);
			list.add(smallMap);
		}

		if (CollUtil.isNotEmpty(residential)) {
			ExportParams residentialParams = new ExportParams();
			residentialParams.setSheetName("住宅小区");
			residentialParams.setTitle("住宅小区火灾隐患排查表");
			Map<String, Object> residentialMap = new HashMap<String, Object>();
			residentialMap.put("title", residentialParams);
			residentialMap.put("data", residential);
			residentialMap.put("entity", DangerExportSmallDto.class);
			list.add(residentialMap);
		}

		if (CollUtil.isNotEmpty(industrial)) {
			ExportParams industrialParams = new ExportParams();
			industrialParams.setSheetName("工业企业");
			industrialParams.setTitle("工业企业火灾隐患排查表");
			Map<String, Object> industrialMap = new HashMap<String, Object>();
			industrialMap.put("title", industrialParams);
			industrialMap.put("data", industrial);
			industrialMap.put("entity", DangerExportIndustrialDto.class);
			list.add(industrialMap);
		}

		if (CollUtil.isNotEmpty(publicPlaces)) {
			ExportParams publicPlacesParams = new ExportParams();
			publicPlacesParams.setSheetName("公共场所");
			publicPlacesParams.setTitle("公共场所火灾隐患排查表");
			Map<String, Object> publicPlacesMap = new HashMap<String, Object>();
			publicPlacesMap.put("title", publicPlacesParams);
			publicPlacesMap.put("data", publicPlaces);
			publicPlacesMap.put("entity", DangerExportIndustrialDto.class);
			list.add(publicPlacesMap);
		}

		if (CollUtil.isNotEmpty(complex)) {
			ExportParams complexParams = new ExportParams();
			complexParams.setSheetName("大型综合体");
			complexParams.setTitle("大型综合体火灾隐患排查表");
			Map<String, Object> complexMap = new HashMap<String, Object>();
			complexMap.put("title", complexParams);
			complexMap.put("data", complex);
			complexMap.put("entity", DangerExportIndustrialDto.class);
			list.add(complexMap);
		}

		Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.XSSF);

		downLoadExcel("高风险隐患台账", response, workbook);
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

		Map<Long, OwnerUnitDangerDataExportDto> dataMap = new HashMap<Long, OwnerUnitDangerDataExportDto>();

		if (CollUtil.isNotEmpty(dangers)) {

			ComputeHighScoreService compute = new ComputeHighScoreService();
			dto.setTotalScore(compute.compute(dangers));

			dangers.forEach((danger) -> {

				OwnerUnitDangerDataExportDto formData = dataMap.get(danger.getFormDataId());
				if (formData == null) {
					formData = new OwnerUnitDangerDataExportDto();
					formData.setMaxScore(danger.getMaxScore());
					dataMap.put(danger.getFormDataId(), formData);
				}

				List<OwnerUnitDangerInfoExportDto> dangersInfos = formData.getDangers();
				if (dangersInfos == null) {
					dangersInfos = new ArrayList<OwnerUnitDangerInfoExportDto>();
					formData.setDangers(dangersInfos);
				}

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

				dangersInfos.add(infoExport);
			});
		}

		dto.setDangers(new ArrayList<OwnerUnitDangerDataExportDto>(dataMap.values()));

		return dto;
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

	private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			workbook.write(response.getOutputStream());
			workbook.close();
		} catch (IOException e) {
			throw new ExcelExportException(e.getMessage());
		}
	}
}
