package com.ruoyi.electrical.report.dto;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.TableTools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormLoopRowTableRenderPolicy extends LoopRowTableRenderPolicy {

	@SuppressWarnings("unchecked")
	@Override
	protected void afterloop(XWPFTable table, Object data) {

		List<DetectFormData> formDatas = (List<DetectFormData>) data;

		for (int i = 1; i <= formDatas.size(); i++) {

			DetectFormData formData = formDatas.get(i - 1);

			if (formData.isMerge()) {

				XWPFTableRow row = table.getRow(i);
				TableTools.mergeCellsHorizonal(table, i, 1, 4);

				CellRenderData cellRenderData = Cells.of(String.valueOf(formData.getFirstContent())).create();
				XWPFTableCell cell = row.getCell(1);

				try {
					CellStyle cellStyle = new CellStyle();
					ParagraphStyle paragraphStyle = ParagraphStyle.builder()
							.withDefaultTextStyle(Style.builder().buildBold().build()).build();
					cellStyle.setDefaultParagraphStyle(paragraphStyle);

					TableRenderPolicy.Helper.renderCell(cell, cellRenderData, cellStyle);
				} catch (Exception e) {
					log.error("", e);
				}
			}
		}
	}
}
