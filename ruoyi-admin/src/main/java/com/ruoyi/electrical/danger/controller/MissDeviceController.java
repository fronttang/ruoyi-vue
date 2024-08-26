package com.ruoyi.electrical.danger.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.electrical.danger.service.IMissDeviceService;
import com.ruoyi.electrical.dto.MissDeviceExportQueryDto;
import com.ruoyi.electrical.dto.MissDeviceJsonDataDto;
import com.ruoyi.electrical.dto.MissDeviceRentalHouseExportDto;
import com.ruoyi.electrical.dto.MissDeviceRentalHouseJson;
import com.ruoyi.electrical.dto.MissDeviceSmallExportDto;
import com.ruoyi.electrical.dto.MissDeviceSmallJson;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

@RestController
@RequestMapping("/miss/device")
public class MissDeviceController extends BaseController {

	@Autowired
	private IMissDeviceService missDeviceService;

	@RequestMapping("/export")
	public void export(OwnerUnitDangerGroupDetailDto data, HttpServletResponse response) {

		List<MissDeviceExportQueryDto> exportData = new ArrayList<MissDeviceExportQueryDto>();

		if (data.getIds() != null && data.getIds().length > 0) {
			exportData.addAll(missDeviceService.exportByUnitId(data.getIds()));
		} else {
			exportData.addAll(missDeviceService.exportByQuery(data));
		}

		// 出租屋
		List<MissDeviceRentalHouseExportDto> rentalHouse = exportData.stream()
				.filter((d) -> "1".equalsIgnoreCase(d.getHighRiskType())).map((d) -> {
					MissDeviceRentalHouseExportDto export = new MissDeviceRentalHouseExportDto();
					BeanUtils.copyProperties(d, export);
					List<MissDeviceJsonDataDto> devices = d.getDevices();

					if (CollUtil.isNotEmpty(devices)) {

						MissDeviceRentalHouseJson total = new MissDeviceRentalHouseJson();

						Field[] fields = MissDeviceRentalHouseJson.class.getDeclaredFields();

						for (int i = 0; i < fields.length; i++) {
							String field = fields[i].getName();
							Optional<Integer> reduce = devices.stream().filter((a) -> Objects.nonNull(a.getDevice()))
									.map((a) -> a.getDevice()).map((b) -> b.getInteger(field))
									.filter((b) -> Objects.nonNull(b)).reduce(Integer::sum);
							BeanUtil.setFieldValue(total, field, reduce.isPresent() ? reduce.get() : null);
						}
						BeanUtils.copyProperties(total, export);
					}
					return export;
				}).collect(Collectors.toList());

		// 三小场所
		List<MissDeviceSmallExportDto> small = exportData.stream()
				.filter((d) -> "2".equalsIgnoreCase(d.getHighRiskType())).map((d) -> {
					MissDeviceSmallExportDto export = new MissDeviceSmallExportDto();
					BeanUtils.copyProperties(d, export);
					List<MissDeviceJsonDataDto> devices = d.getDevices();

					if (CollUtil.isNotEmpty(devices)) {

						MissDeviceSmallJson total = new MissDeviceSmallJson();

						Field[] fields = MissDeviceSmallJson.class.getDeclaredFields();

						for (int i = 0; i < fields.length; i++) {
							String field = fields[i].getName();
							Optional<Integer> reduce = devices.stream().filter((a) -> Objects.nonNull(a.getDevice()))
									.map((a) -> a.getDevice()).map((b) -> b.getInteger(field))
									.filter((b) -> Objects.nonNull(b)).reduce(Integer::sum);
							BeanUtil.setFieldValue(total, field, reduce.isPresent() ? reduce.get() : null);
						}
						BeanUtils.copyProperties(total, export);
					}
					return export;
				}).collect(Collectors.toList());

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (CollUtil.isNotEmpty(rentalHouse)) {
			ExportParams rentalHouseParams = new ExportParams();
			rentalHouseParams.setSheetName("出租屋");
			rentalHouseParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> rentalHouseMap = new HashMap<String, Object>();
			rentalHouseMap.put("title", rentalHouseParams);
			rentalHouseMap.put("data", rentalHouse);
			rentalHouseMap.put("entity", MissDeviceRentalHouseExportDto.class);
			list.add(rentalHouseMap);
		}

		if (CollUtil.isNotEmpty(small)) {
			ExportParams smallParams = new ExportParams();
			smallParams.setSheetName("三小场所");
			smallParams.setStyle(DangerExcelExportStylerImpl.class);

			Map<String, Object> smallMap = new HashMap<String, Object>();
			smallMap.put("title", smallParams);
			smallMap.put("data", small);
			smallMap.put("entity", MissDeviceSmallExportDto.class);
			list.add(smallMap);
		}

		Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.XSSF);

		downLoadExcel("缺失设备台账", response, workbook);
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
