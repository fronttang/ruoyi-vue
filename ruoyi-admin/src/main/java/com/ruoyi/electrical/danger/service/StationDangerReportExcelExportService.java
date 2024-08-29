package com.ruoyi.electrical.danger.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import cn.hutool.core.collection.CollUtil;

public class StationDangerReportExcelExportService extends ExcelExportService {

	private void buildExcelExportEntity(List<ExcelExportEntity> entity, List<ExcelExportEntity> entitys) {
		entity.forEach((data) -> {
			if ("隐患".equalsIgnoreCase(data.getName()) && CollUtil.isNotEmpty(data.getList())) {
				buildExcelExportEntity(data.getList(), entitys);
			} else {

				entitys.add(data);
			}
		});
	}

	protected int createHeaderAndTitle(ExportParams entity, Sheet sheet, Workbook workbook,
			List<ExcelExportEntity> excelParams) {

		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();

		buildExcelExportEntity(excelParams, entitys);

		int rows = 0, fieldLength = getFieldLength(excelParams);
		if (entity.getTitle() != null) {
			rows += createTitle2Row(entity, sheet, workbook, fieldLength);
		}
		createHeaderRow(entity, sheet, workbook, rows, entitys, 0);
		rows += getRowNums(entitys, true);
		if (entity.isFixedTitle()) {
			sheet.createFreezePane(0, rows, 0, rows);
		}
		return rows;
	}

	/**
	 * 创建表头
	 */
	private int createHeaderRow(ExportParams title, Sheet sheet, Workbook workbook, int index,
			List<ExcelExportEntity> excelParams, int cellIndex) {

		Row row = sheet.getRow(index) == null ? sheet.createRow(index) : sheet.getRow(index);
		int rows = getRowNums(excelParams, true);
		row.setHeight(title.getHeaderHeight());
		Row listRow = null;
		if (rows >= 2) {
			listRow = sheet.getRow(index + 1);
			if (listRow == null) {
				listRow = sheet.createRow(index + 1);
				listRow.setHeight(title.getHeaderHeight());

			}
		}
		int groupCellLength = 0;
		CellStyle titleStyle = getExcelExportStyler().getTitleStyle(title.getColor());
		for (int i = 0, exportFieldTitleSize = excelParams.size(); i < exportFieldTitleSize; i++) {
			ExcelExportEntity entity = excelParams.get(i);
			// 加入换了groupName或者结束就，就把之前的那个换行
			if (StringUtils.isBlank(entity.getGroupName()) || i == 0
					|| !entity.getGroupName().equals(excelParams.get(i - 1).getGroupName())) {
				if (groupCellLength > 1) {
					sheet.addMergedRegion(
							new CellRangeAddress(index, index, cellIndex - groupCellLength, cellIndex - 1));
				}
				groupCellLength = 0;
			}
			if (StringUtils.isNotBlank(entity.getGroupName())) {
				createStringCell(row, cellIndex, entity.getGroupName(), titleStyle, entity);
				createStringCell(listRow, cellIndex, entity.getName(), titleStyle, entity);
				groupCellLength++;
			} else if (StringUtils.isNotBlank(entity.getName())) {
				createStringCell(row, cellIndex, entity.getName(), titleStyle, entity);
			}
			if (entity.getList() != null) {
				// 保持原来的
				int tempCellIndex = cellIndex;
				cellIndex = createHeaderRow(title, sheet, workbook, rows == 1 ? index : index + 1, entity.getList(),
						cellIndex);
				List<ExcelExportEntity> sTitel = entity.getList();
				if (StringUtils.isNotBlank(entity.getName()) && sTitel.size() > 1) {
					PoiMergeCellUtil.addMergedRegion(sheet, index, index, tempCellIndex,
							tempCellIndex + getFieldLength(sTitel));
				}
				/*
				 * for (int j = 0, size = sTitel.size(); j < size; j++) {
				 * 
				 * createStringCell(rows == 2 ? listRow : row, cellIndex,
				 * sTitel.get(j).getName(), titleStyle, entity); cellIndex++; }
				 */
				cellIndex--;
			} else if (rows > 1 && StringUtils.isBlank(entity.getGroupName())) {
				createStringCell(listRow, cellIndex, "", titleStyle, entity);
				PoiMergeCellUtil.addMergedRegion(sheet, index, index + rows - 1, cellIndex, cellIndex);
			}
			cellIndex++;
		}
		if (groupCellLength > 1) {
			PoiMergeCellUtil.addMergedRegion(sheet, index, index, cellIndex - groupCellLength, cellIndex - 1);
		}
		return cellIndex;
	}
}
