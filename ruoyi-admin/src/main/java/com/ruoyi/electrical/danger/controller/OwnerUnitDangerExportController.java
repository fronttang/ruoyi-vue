package com.ruoyi.electrical.danger.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportHighService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportIndustrialService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportReviewUrbanVillageService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportStationService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportUrbanVillageService;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IProjectService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/owner/unit/danger")
public class OwnerUnitDangerExportController extends BaseController {

	@Autowired
	private OwnerUnitDangerExportHighService exportHighService;

	@Autowired
	private OwnerUnitDangerExportUrbanVillageService urbanVillageService;

	@Autowired
	private OwnerUnitDangerExportReviewUrbanVillageService exportReviewUrbanVillageService;

	@Autowired
	private OwnerUnitDangerExportIndustrialService exportIndustrialService;

	@Autowired
	private OwnerUnitDangerExportStationService exportStationService;

	@Autowired
	private IProjectService projectService;

	@RequestMapping("/export")
	public AjaxResult export(OwnerUnitDangerGroupDetailDto data, HttpServletResponse response) {

		Project project = projectService.selectProjectById(data.getProjectId());
		if (project == null) {
			return AjaxResult.error();
		}

		String projectType = project.getType();

		Workbook workbook = null;
		if ("1".equalsIgnoreCase(projectType)) {
			// 城中村
			workbook = urbanVillageService.exportDanger(data);
			setCellBorder(workbook);
			return saveFile("城中村出租屋用电安全初检隐患台账明细表", workbook);
		} else if ("2".equalsIgnoreCase(projectType)) {
			// 工业园
			workbook = exportIndustrialService.exportDanger(data);
			setCellBorder(workbook);
			return saveFile("工业园区电气检测隐患台账", workbook);
		} else if ("3".equalsIgnoreCase(projectType)) {
			// 高风险
			workbook = exportHighService.exportDanger(data);
			setCellBorder(workbook);
			return saveFile("高风险隐患台账", workbook);
		} else if ("4".equalsIgnoreCase(projectType)) {
			// 充电站
			workbook = exportStationService.exportDanger(data);
			setCellBorder(workbook);
			return saveFile("充电站隐患台账", workbook);
		}

		return AjaxResult.error();
	}

	@RequestMapping("/export/review")
	public AjaxResult exportReview(OwnerUnitDangerGroupDetailDto data, HttpServletResponse response) {

		Project project = projectService.selectProjectById(data.getProjectId());
		if (project == null) {
			return AjaxResult.error();
		}

		String projectType = project.getType();
		if ("1".equalsIgnoreCase(projectType)) {
			// 城中村
			Workbook workbook = exportReviewUrbanVillageService.exportDanger(data);
			setCellBorder(workbook);
			// downLoadExcel("城中村出租屋用电安全隐患台账明细表", response, workbook);
			return saveFile("城中村出租屋用电安全复检隐患台账明细表", workbook);
		}

		return AjaxResult.error();

	}

	private void setCellBorder(Workbook workbook) {
		for (Sheet sheet : workbook) {
			for (Row row : sheet) {
				for (Cell cell : row) {
					CellStyle cellStyle = cell.getCellStyle();
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);

					cell.setCellStyle(cellStyle);
				}
			}
		}
	}

	private AjaxResult saveFile(String fileName, Workbook workbook) {
		BufferedOutputStream bufferedOutputStream = null;
		try {

			AjaxResult success = AjaxResult.success();

			String filePath = RuoYiConfig.getUploadPath();

			LocalDateTime now = LocalDateTime.now();
			String datePath = DateUtil.format(now, "yyyy/MM/dd");
			String uuid = IdUtil.getSnowflakeNextIdStr();
			fileName = StrUtil.format("{}/{}/{}.xlsx", datePath, uuid, fileName);

			File saveFile = FileUploadUtils.getAbsoluteFile(filePath, fileName);

			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(saveFile));

			workbook.write(bufferedOutputStream);
			workbook.close();
			bufferedOutputStream.close();

			success.put("data", FileUploadUtils.getPathFileName(filePath, fileName));

			return success;
		} catch (Exception e) {
			log.error("", e);
			return AjaxResult.error();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
	}
}
