package com.ruoyi.electrical.danger.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.electrical.danger.service.DangerReportExcelExportService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportHighService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportIndustrialService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportReviewUrbanVillageService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportUrbanVillageService;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IProjectService;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;

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
	private IProjectService projectService;

	@RequestMapping("/export")
	public void export(OwnerUnitDangerGroupDetailDto data, HttpServletResponse response) {

		Project project = projectService.selectProjectById(data.getProjectId());
		if (project == null) {
			return;
		}

		String projectType = project.getType();
		if ("1".equalsIgnoreCase(projectType)) {
			// 城中村
			List<Map<String, Object>> exportList = urbanVillageService.exportDanger(data);
			downLoadExcel("城中村出租屋用电安全隐患台账明细表", response, exportList);
		} else if ("2".equalsIgnoreCase(projectType)) {
			// 工业园
			List<Map<String, Object>> exportList = exportIndustrialService.exportDanger(data);
			downLoadExcel("工业园区电气检测隐患台账", response, exportList);
		} else if ("3".equalsIgnoreCase(projectType)) {
			// 高风险
			List<Map<String, Object>> exportList = exportHighService.exportDanger(data);
			downLoadExcel("高风险隐患台账", response, exportList);
		} else if ("4".equalsIgnoreCase(projectType)) {
			// 充电站
		}
	}

	@RequestMapping("/export/review")
	public void exportReview(OwnerUnitDangerGroupDetailDto data, HttpServletResponse response) {

		Project project = projectService.selectProjectById(data.getProjectId());
		if (project == null) {
			return;
		}

		String projectType = project.getType();
		if ("1".equalsIgnoreCase(projectType)) {
			// 城中村
			List<Map<String, Object>> exportList = exportReviewUrbanVillageService.exportDanger(data);
			downLoadExcel("城中村出租屋用电安全隐患台账明细表", response, exportList);
		}
	}

	private void downLoadExcel(String fileName, HttpServletResponse response, List<Map<String, Object>> exportList) {
		Workbook workbook = exportExcel(exportList, ExcelType.XSSF);
		downLoadExcel(fileName, response, workbook);
	}

	/**
	 * 根据Map创建对应的Excel(一个excel 创建多个sheet)
	 *
	 * @param list 多个Map key title 对应表格Title key entity 对应表格对应实体 key data Collection
	 *             数据
	 * @return
	 */
	private Workbook exportExcel(List<Map<String, Object>> list, ExcelType type) {
		Workbook workbook = getWorkbook(type, 0);
		for (Map<String, Object> map : list) {
			DangerReportExcelExportService service = new DangerReportExcelExportService();
			ExportParams params = (ExportParams) map.get("title");
			params.setType(type);
			service.createSheet(workbook, params, (Class<?>) map.get("entity"), (Collection<?>) map.get("data"));
		}
		return workbook;
	}

	private Workbook getWorkbook(ExcelType type, int size) {
		if (ExcelType.HSSF.equals(type)) {
			return new HSSFWorkbook();
		} else {
			return new XSSFWorkbook();
		}
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
