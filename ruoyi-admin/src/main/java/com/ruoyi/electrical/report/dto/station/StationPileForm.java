package com.ruoyi.electrical.report.dto.station;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StationPileForm {

	/**
	 * 非车载/交流
	 */
	private String name;

	/**
	 * 
	 */
	private List<StationPileFormDatas> pileFormDatas = new ArrayList<StationPileFormDatas>();

	@Data
	public static class StationPileFormDatas {

		/**
		 * 检测数据
		 */
		private List<StationPileFormData> pileFormData = new ArrayList<StationPileFormData>();
	}

	@Data
	public static class StationPileFormData {

		/**
		 * 一级编号
		 */
		private String firstCode;

		/**
		 * 一级编号内容
		 */
		private Object firstContent;

		private String pileName1 = "";

		private String result1;

		private String pileName2 = "";

		private String result2;

		private String pileName3 = "";

		private String result3;

		private String pileName4 = "";

		private String result4;

	}
}
