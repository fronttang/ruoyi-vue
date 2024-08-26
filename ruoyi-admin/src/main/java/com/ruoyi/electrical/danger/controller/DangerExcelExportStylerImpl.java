package com.ruoyi.electrical.danger.controller;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;

public class DangerExcelExportStylerImpl extends ExcelExportStylerDefaultImpl {

	private Workbook workbook;

	public DangerExcelExportStylerImpl(Workbook workbook) {
		super(workbook);
		this.workbook = workbook;
	}

	@Override
	public CellStyle getTitleStyle(short color) {
		CellStyle titleStyle = super.getTitleStyle(color);

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
		CellStyle titleStyle = super.getHeaderStyle(color);
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 14);

		titleStyle.setFont(font);
		titleStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);

		return titleStyle;
	}

//	@Override
//	public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
//		CellStyle style = super.stringSeptailStyle(workbook, isWarp);
//
//		style.setBorderBottom(BorderStyle.THIN);
//		style.setBorderTop(BorderStyle.THIN);
//		style.setBorderLeft(BorderStyle.THIN);
//		style.setBorderRight(BorderStyle.THIN);
//
//		return style;
//	}
//
//	@Override
//	public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
//		CellStyle style = super.stringNoneStyle(workbook, isWarp);
//
//		style.setBorderBottom(BorderStyle.THIN);
//		style.setBorderTop(BorderStyle.THIN);
//		style.setBorderLeft(BorderStyle.THIN);
//		style.setBorderRight(BorderStyle.THIN);
//
//		return style;
//	}

}
