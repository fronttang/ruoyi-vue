package com.ruoyi.electrical.danger.service;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;

public class StationDangerExcelExportStylerImpl extends ExcelExportStylerDefaultImpl {

	private Workbook workbook;

	public StationDangerExcelExportStylerImpl(Workbook workbook) {
		super(workbook);
		this.workbook = workbook;
	}

	@Override
	public CellStyle getTitleStyle(short color) {
		CellStyle titleStyle = super.getTitleStyle(color);

		titleStyle.setWrapText(true);

		titleStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Font font = workbook.createFont();
		font.setBold(true);

		titleStyle.setFont(font);

		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);

		return titleStyle;
	}

	@Override
	public CellStyle getHeaderStyle(short color) {
		CellStyle style = super.getHeaderStyle(color);
		style.setWrapText(true);
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 14);

		style.setFont(font);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}

	@Override
	public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
		CellStyle style = super.stringSeptailStyle(workbook, isWarp);

		style.setWrapText(true);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}

	@Override
	public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
		CellStyle style = super.stringNoneStyle(workbook, isWarp);

		style.setWrapText(true);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}

}
