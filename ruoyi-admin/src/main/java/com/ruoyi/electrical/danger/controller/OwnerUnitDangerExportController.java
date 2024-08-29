package com.ruoyi.electrical.danger.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.electrical.danger.service.DangerReportExcelExportService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportHighService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportIndustrialService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportReviewUrbanVillageService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportStationService;
import com.ruoyi.electrical.danger.service.OwnerUnitDangerExportUrbanVillageService;
import com.ruoyi.electrical.danger.service.StationDangerReportExcelExportService;
import com.ruoyi.electrical.dto.DangerExportStationDto;
import com.ruoyi.electrical.dto.DangerExportStationDto.DangerExportStationDangerDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IProjectService;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import cn.hutool.core.collection.CollUtil;

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
			List<DangerExportStationDto> exportDatas = exportStationService.exportDanger(data);

			LinkedList<DangerExportStationDto> exportDataList = new LinkedList<DangerExportStationDto>(exportDatas);

			ExportParams exportParams = new ExportParams();
			// rentalHouseParams.setCreateHeadRows(false);
			exportParams.setSheetName("隐患台账");
			exportParams.setTitle("充电站隐患台账");

			Workbook workbook = getWorkbook(ExcelType.XSSF, 0);
			StationDangerReportExcelExportService service = new StationDangerReportExcelExportService();
			service.createSheet(workbook, exportParams, DangerExportStationDto.class, exportDatas);

			Sheet sheet = workbook.getSheetAt(0);
			int index = 4;
			for (int i = 0; i < exportDataList.size(); i++) {
				DangerExportStationDto exportData = exportDataList.get(i);

				int size = 1;

				if (CollUtil.isNotEmpty(exportData.getDangers())) {

					List<DangerExportStationDangerDto> dangers = exportData.getDangers();

					size = dangers.size();

					PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 7, 7);
					PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 8, 8);
					PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 9, 9);
					PoiMergeCellUtil.addMergedRegion(sheet, index, index + size - 1, 10, 10);

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
							CellRangeAddress region = new CellRangeAddress(index + j, index + j, 16, 21);
							sheet.addMergedRegion(region);
							row.getCell(16).setCellStyle(getStationDangerTitleStyle(workbook));
						}
					}
				}
				index = index + size;
			}

			downLoadExcel("充电站隐患台账", response, workbook);
		}
	}

	private CellStyle getStationDangerTitleStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);

		Font font = workbook.createFont();
		font.setBold(true);

		style.setFont(font);
		return style;
	}

	private CellStyle getStationDangerStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		return style;
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
