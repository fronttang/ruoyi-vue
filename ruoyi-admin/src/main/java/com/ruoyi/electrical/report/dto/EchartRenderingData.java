package com.ruoyi.electrical.report.dto;

import java.util.List;

import lombok.Data;

@Data
public class EchartRenderingData {

	private Boolean base64 = true;

	private String chartType = "option3";

	private List<EchartsReportData> data;

	private Integer width = 510;

	private Integer height = 316;

	@Data
	public static class EchartsReportData {

		private Long value;

		private String name;

		private String rate;
	}
}
