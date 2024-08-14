package com.ruoyi.electrical.report.dto.station;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.TableTools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StationDangerTableRenderPolicy extends LoopRowTableRenderPolicy {

	private StationOwnerUnitInfo ownerUnit;

	public StationDangerTableRenderPolicy(StationOwnerUnitInfo ownerUnit) {
		this.ownerUnit = ownerUnit;
	}

	@Override
	protected void afterloop(XWPFTable table, Object data) {

		@SuppressWarnings("unchecked")
		List<StationDanger> dangers = (List<StationDanger>) data;

		for (int i = 1; i <= dangers.size(); i++) {
			StationDanger danger = dangers.get(i - 1);
			// 合并水平单元格
			if (danger.isMerge()) {

				XWPFTableRow row = table.getRow(i);
				row.createCell();

				TableTools.mergeCellsHorizonal(table, i, 0, 3);
				RowRenderData rowRenderData = Rows.of(danger.getLocation()).create();
				try {
					TableRenderPolicy.Helper.renderRow(row, rowRenderData, Style.builder().buildBold().build());
				} catch (Exception e) {
					log.error("", e);
				}
			}
		}

		try {
			int scoreRowIdx = 2 + dangers.size();
			XWPFTableRow row = table.getRow(scoreRowIdx);
			XWPFTableCell cell = row.getCell(1);
			CellRenderData scoreCellRenderData = Cells.of(String.valueOf(this.ownerUnit.getScore())).create();
			TableRenderPolicy.Helper.renderCell(cell, scoreCellRenderData, null);

			XWPFTableCell cell3 = row.getCell(3);

			CellRenderData resultCellRenderData = Cells.of(this.ownerUnit.getResult()).create();
			TableRenderPolicy.Helper.renderCell(cell3, resultCellRenderData, null);
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
