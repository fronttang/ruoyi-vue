package com.ruoyi.electrical.report.dto.station;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ClassPathResource;

import com.deepoove.poi.data.ByteArrayPictureRenderData;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.ByteUtils;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.electrical.report.dto.station.StationPileForm.StationPileFormData;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StationPileFormTableRenderPolicy extends LoopRowTableRenderPolicy {

	@Override
	protected void afterloop(XWPFTable table, Object data) {

		@SuppressWarnings("unchecked")
		List<StationPileFormData> formDatas = (List<StationPileFormData>) data;

		if (CollUtil.isNotEmpty(formDatas)) {

			try {
				StationPileFormData stationPileFormData = formDatas.get(0);

				CellStyle cellStyle = new CellStyle();
				ParagraphStyle paragraphStyle = ParagraphStyle.builder()
						.withDefaultTextStyle(Style.builder().buildBold().build()).build();
				cellStyle.setDefaultParagraphStyle(paragraphStyle);

				XWPFTableRow row0 = table.getRow(0);

				XWPFTableCell cell2 = row0.getCell(2);
				CellRenderData cellRenderData2 = Cells.of(StrUtil.format("桩体编号：{}", stationPileFormData.getPileName1()))
						.create();
				TableRenderPolicy.Helper.renderCell(cell2, cellRenderData2, cellStyle);

				XWPFTableCell cell3 = row0.getCell(3);
				CellRenderData cellRenderData3 = Cells.of(StrUtil.format("桩体编号：{}", stationPileFormData.getPileName2()))
						.create();
				TableRenderPolicy.Helper.renderCell(cell3, cellRenderData3, cellStyle);

				XWPFTableCell cell4 = row0.getCell(4);
				CellRenderData cellRenderData4 = Cells.of(StrUtil.format("桩体编号：{}", stationPileFormData.getPileName3()))
						.create();
				TableRenderPolicy.Helper.renderCell(cell4, cellRenderData4, cellStyle);

				XWPFTableCell cell5 = row0.getCell(5);
				CellRenderData cellRenderData5 = Cells.of(StrUtil.format("桩体编号：{}", stationPileFormData.getPileName4()))
						.create();
				TableRenderPolicy.Helper.renderCell(cell5, cellRenderData5, cellStyle);

				for (int i = 1; i <= formDatas.size(); i++) {

					StationPileFormData formData = formDatas.get(i - 1);
					if ("3.2.14".equalsIgnoreCase(formData.getFirstCode())) {
						XWPFTableRow row = table.getRow(i + 1);
						XWPFTableCell cell = row.getCell(1);

						InputStream inputStream = ClassPathResource.class.getClassLoader()
								.getResourceAsStream("report/initial/station/3.2.14.jpg");

						ByteArrayPictureRenderData pic = new ByteArrayPictureRenderData(
								ByteUtils.toByteArray(inputStream));
						PictureStyle pictureStyle = new PictureStyle();
						pictureStyle.setScalePattern(WidthScalePattern.FIT);
						pic.setPictureStyle(pictureStyle);

						CellRenderData cellRenderData = Cells.of(pic).create();
						TableRenderPolicy.Helper.renderCell(cell, cellRenderData, null);
					}
				}

			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

}
